package com.bosong.ball_light.model.bean;

import android.graphics.Bitmap;

public class ContextMemberBean {
	private Bitmap bitmap;
	private String name;
	private boolean isOnline;

	public ContextMemberBean(Bitmap bitmap, String name, boolean isOnline) {
		super();
		this.bitmap = bitmap;
		this.name = name;
		this.isOnline = isOnline;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
