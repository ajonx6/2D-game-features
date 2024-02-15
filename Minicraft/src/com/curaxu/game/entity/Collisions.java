package com.curaxu.game.entity;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.components.AABBBoxComponent;
import com.curaxu.game.entity.components.MoveComponent;

public class Collisions {
	public static class CollisionData {
		public Vector contactPoint, contactNormal;
		public double contactTime;

		public CollisionData(Vector contactPoint, Vector contactNormal, double contactTime) {
			this.contactPoint = contactPoint;
			this.contactNormal = contactNormal;
			this.contactTime = contactTime;
		}
	}

	public static boolean PointVsBox(Vector pos, AABBBox a) {
		return pos.getX() >= a.getPosition().getX() && pos.getY() >= a.getPosition().getY() && pos.getX() < a.getPosition().getX() + a.getWidth() && pos.getY() < a.getPosition().getY() + a.getHeight();
	}

	public static boolean BoxVsBox(AABBBox a, AABBBox b) {
		return a.getPosition().getX() < b.getPosition().getX() + b.getWidth() &&
				a.getPosition().getX() + a.getWidth() > b.getPosition().getX() &&
				a.getPosition().getY() < b.getPosition().getY() + b.getHeight() &&
				a.getPosition().getY() + a.getHeight() > b.getPosition().getY();
	}

	public static CollisionData RayVSBox(Vector rayOrigin, Vector rayDirection, AABBBox a) {
		// if (rayDirection.isZero()) return null;
		rayDirection = rayDirection.normalize();
		// System.out.println("RAY");
		// System.out.println(rayOrigin+", "+rayDirection);
		
		Vector tNear = a.getPosition().sub(rayOrigin).div(rayDirection);
		Vector tFar = a.getPosition().add(a.getSize()).sub(rayOrigin).div(rayDirection);
		
		if (tNear.getX() > tFar.getX()) {
			double temp = tNear.getX();
			tNear.setX(tFar.getX());
			tFar.setX(temp);
		}
		if (tNear.getY() > tFar.getY()) {
			double temp = tNear.getY();
			tNear.setY(tFar.getY());
			tFar.setY(temp);
		}

		if (tNear.getX() > tFar.getY() || tNear.getY() > tFar.getX()) return null;

		double tHitNear = Math.max(tNear.getX(), tNear.getY());
		double tHitFar = Math.min(tFar.getX(), tFar.getY());
		if (tHitFar < 0) return null;

		Vector contactPoint = rayOrigin.add(rayDirection.mul(tHitNear));
		Vector contactNormal = null;
		if (tNear.getX() > tNear.getY()) {
			if (rayDirection.getX() < 0) contactNormal = new Vector(1, 0);
			else contactNormal = new Vector(-1, 0);
		} else if (tNear.getX() < tNear.getY()) {
			if (rayDirection.getY() < 0) contactNormal = new Vector(0, 1);
			else contactNormal = new Vector(0, -1);
		}

		// System.out.println("T:"+tNear + ", " + tFar);
		// System.out.println("H:"+tHitNear+", " +tHitFar);
		// System.out.println("O:"+contactNormal + ", " + rayDirection);

		return new CollisionData(contactPoint, contactNormal, tHitNear);
	}

	public static CollisionData DynamicBoxVsBox(AABBBox a, Vector velocity, AABBBox b, double delta) {
		Vector newBoxPosition = b.getPosition().sub(a.getSize().div(2));
		Vector newBoxSize = b.getSize().add(a.getSize());
		AABBBox expandedBox = new AABBBox(newBoxPosition, newBoxSize);

		CollisionData collisionData = RayVSBox(a.getPosition().add(a.getSize().div(2)), velocity, expandedBox);
		if (collisionData != null && collisionData.contactTime <= 1.0) return collisionData;
		else return null;
	}
}
