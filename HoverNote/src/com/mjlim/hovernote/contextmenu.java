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
import android.widget.ImageView;
import android.widget.LinearLayout;

public class contextmenu extends FloatingModalWindow implements OnClickListener{

	ImageView bPaste, bCopy, bClose, bMini, bShare, bSave, bSettings;
	
	public contextmenu(Context context, HoverNoteView ov, WindowManager wm, int x, int y) {
		super(context, ov, wm, x, y);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.contextmenu, this);
		
		// Retrieve UI elements
		bCopy = (ImageView)findViewById(R.id.bCopy);
		bClose = (ImageView)findViewById(R.id.bClose);
		bMini = (ImageView)findViewById(R.id.bMini);
		bPaste = (ImageView)findViewById(R.id.bPaste);
		bShare = (ImageView)findViewById(R.id.bShare);
		bSave = (ImageView)findViewById(R.id.bSave);
		bSettings = (ImageView)findViewById(R.id.bSettings);
		
		// Assign listeners
	
		// Set onclick for all of the buttons
		bCopy.setOnClickListener(this);
		bClose.setOnClickListener(this);
		bMini.setOnClickListener(this);
		bPaste.setOnClickListener(this);
		bShare.setOnClickListener(this);
		bSave.setOnClickListener(this);
		bSettings.setOnClickListener(this);
		
		this.invalidate();
		wm.addView(this, winparams); // make it visible
		this.requestFocus();	
	}


	public void onClick(View v) {
		if(v==bMini){
			this.dismiss();
			noteView.minimize();
		}else if(v==bCopy){
			this.dismiss();
			noteView.copy();
		}else if(v==bPaste){
			this.dismiss();
			noteView.paste();
		}else if(v==bShare){
			this.dismiss();
			noteView.share();
		}else if(v==bSave){
			this.dismiss();
			noteView.showSave();
		}else if(v==bSettings){
			this.dismiss();
			noteView.showSettings();
		}else if(v==bClose){
			this.dismiss();
			noteView.setWindowAnimation(android.R.style.Animation_Dialog);
			noteView.close();
		}
		
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// needs to also close when hitting menu
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
	

}
