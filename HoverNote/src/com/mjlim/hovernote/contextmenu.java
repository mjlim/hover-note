/*
 * Copyright 2012 Mike Lim
 * 
 * This file is part of hovernote.
 *
 *  hovernote is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  hovernote is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with hovernote.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mjlim.hovernote;

import com.mjlim.hovernote.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class contextmenu extends LinearLayout implements OnKeyListener, OnTouchListener, OnClickListener{

	private WindowManager wm;
	private WindowManager.LayoutParams winparams;

	HoverNoteView ov;
	ImageView bPaste, bCopy, bClose, bMini;
	
	public contextmenu(Context context, HoverNoteView ov, WindowManager wm, int x, int y) {
		super(context);
		// TODO Auto-generated constructor stub
		
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.contextmenu, this);
		
		Drawable drActiveRect = this.getResources().getDrawable(R.drawable.activerectangle);
		
		this.setBackgroundDrawable(drActiveRect);
		
		this.ov=ov;
		this.wm = wm;
		
		winparams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_PHONE |
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
				WindowManager.LayoutParams.FLAG_DIM_BEHIND |
				WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		
		winparams.dimAmount = 0.0f; // yes, this is indeed a dim with no amount. hackish way to stop presses intended to get rid of the menu from going through to apps underneath.
		
		winparams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL; // set this flag on
		winparams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		winparams.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		winparams.gravity = Gravity.LEFT | Gravity.TOP;
		winparams.setTitle("Context Menu");
		winparams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		winparams.width = WindowManager.LayoutParams.WRAP_CONTENT;

		winparams.x=x;
		winparams.y=y;
		
		winparams.windowAnimations = android.R.style.Animation_Toast;
		
		winparams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
	
		// Retrieve UI elements
		//Buttons
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);

		bCopy = (ImageView)findViewById(R.id.bCopy);
		bClose = (ImageView)findViewById(R.id.bClose);
		bMini = (ImageView)findViewById(R.id.bMini);
		bPaste = (ImageView)findViewById(R.id.bPaste);
//		((EditText)findViewById(R.id.editText1)).setOnKeyListener(this);
		// Assign listeners
		this.setOnTouchListener(this);
		this.setOnKeyListener(this);
		
//		bCopy.setOnKeyListener(this);
//		bClose.setOnKeyListener(this);
//		
		bCopy.setOnClickListener(this);
		bClose.setOnClickListener(this);
		bMini.setOnClickListener(this);
		bPaste.setOnClickListener(this);
		
		this.invalidate();
		wm.addView(this, winparams);

		this.requestFocus();		
	}

	public boolean onTouch(View v, MotionEvent me) {
		// TODO Auto-generated method stub
		if((me.getActionMasked() & (MotionEvent.ACTION_OUTSIDE | MotionEvent.ACTION_DOWN)) == (MotionEvent.ACTION_OUTSIDE | MotionEvent.ACTION_DOWN)){
			this.dismiss();
			return true;
		}
		
		return false;
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==bMini){
			this.dismiss();
			ov.setWindowAnimation(android.R.style.Animation_Translucent);
			ov.createNotif();
			ov.close();
		}else if(v==bCopy){
			this.dismiss();
			ov.copy();
		}else if(v==bPaste){
			this.dismiss();
			ov.paste();
		}else if(v==bClose){
			this.dismiss();
			ov.setWindowAnimation(android.R.style.Animation_Dialog);
			ov.close();
		}
		
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
//		this.dismiss();
		if(event.getAction() == KeyEvent.ACTION_UP){
			switch(keyCode){
			case KeyEvent.KEYCODE_BACK:
			case KeyEvent.KEYCODE_MENU:
				this.dismiss();
				return true;

			}
		}
		return false;
		
	}
	

	
	public void dismiss(){
		wm.removeView(this);
		ov.focus();
		
	}



}
