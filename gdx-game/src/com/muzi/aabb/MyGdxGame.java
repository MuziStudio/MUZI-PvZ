package com.muzi.aabb;
//beta v0.1 by 我的浓you酸
//讨论请加qq:1329954164
//本源码仅作参考，禁止商业用途
//版本 v0.1  完成日期 2020.4.10  20:42
//类似pvz的libgdx小游戏
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import java.security.*;
import java.util.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class MyGdxGame extends Game
{   float CameraSize=800,time,t=0,s=0;
    boolean isfail=false;
	Texture wandou,zidan,guaitu,ditu;
    int sun=100;
	SpriteBatch batch;
   // ShapeRenderer rend;
	BitmapFont font;
	OrthographicCamera camera;
	ArrayList<Nai> nai;
	ArrayList<Ball> ball;
	ArrayList<Dan> dan;
	ArrayList<Guai> guai;
	@Override
	public void create()
	{   wandou=new Texture(Gdx.files.internal("dou.png"));
	    zidan=new Texture(Gdx.files.internal("dan.png"));
		guaitu=new Texture(Gdx.files.internal("guai.png"));
		ditu=new Texture(Gdx.files.internal("ditu.png"));
		camera = new OrthographicCamera();
		reastcamera();
		nai=new ArrayList<Nai>();
        ball=new ArrayList<Ball>();
		dan=new ArrayList<Dan>();
		guai=new ArrayList<Guai>();
		batch = new SpriteBatch();
		font=new BitmapFont();
		//rend = new ShapeRenderer();
		//addguai(1200,600,5);
	}

	@Override
	public void render()
	{        
	    Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//rend.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		/*rend.begin(ShapeRenderer.ShapeType.Filled);
		rend.end();*/
		batch.begin();
		batch.draw(ditu,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		for(int a=0;a<nai.size();a++){font.setColor(Color.YELLOW);font.draw(batch,""+nai.get(a).text,nai.get(a).x,Gdx.graphics.getHeight()- nai.get(a).y);}
		for(int a=0;a<guai.size();a++){batch.draw(guaitu,guai.get(a).x,Gdx.graphics.getHeight()-guai.get(a).y,guai.get(a).r*2,guai.get(a).r*2);}
		for(int a=0;a<dan.size();a++){batch.draw(zidan,dan.get(a).x,Gdx.graphics.getHeight()- dan.get(a).y,dan.get(a).r*2,dan.get(a).r*2);}
		for(int a=0;a<ball.size();a++){batch.draw(wandou,ball.get(a).x-80,Gdx.graphics.getHeight()- ball.get(a).y-82,160,160);}
		font.setColor(Color.WHITE);
		font.setScale(6f);
		font.draw(batch,"Sun:"+sun,Gdx.graphics.getWidth()/2-800,Gdx.graphics.getHeight()-100);
		font.setScale(4f);
		font.setColor(Color.RED);
		font.draw(batch,"size1:"+ball.size()+"  size2:"+dan.size()+" By muzi DEBUG ",Gdx.graphics.getWidth()/2-100*font.getScaleX(),100);
		if(isfail){font.setScale(15f); font.draw(batch,"FAIL",Gdx.graphics.getWidth()/2-200,Gdx.graphics.getHeight()/2);if(Gdx.input.justTouched()){isfail=false;sun=100;}}
		batch.end();
		if(Gdx.input.justTouched()){if(sun>=20){addball((float)Gdx.input.getX(),(float)Gdx.input.getY(),60);sun-=20;addText((float)Gdx.input.getX()-40,(float) Gdx.input.getY()-120,""+60);}}
		time += Gdx.graphics.getDeltaTime();
	if(isfail==false){
		logic();}
		
	}
	private void reastcamera()
	{
		if (Gdx.graphics.getHeight() < Gdx.graphics.getWidth())
		{CameraSize=Gdx.graphics.getWidth();
			camera.setToOrtho(false, CameraSize, CameraSize * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());}
		else
		{CameraSize=Gdx.graphics.getHeight();
			camera.setToOrtho(false, CameraSize * Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), CameraSize);
		}}
		public void addball(float x,float y,float r){
			Ball b=new Ball();
			b.x=x;b.y=y;b.r=r;
			ball.add(b);
		}
		public void addText(float x,float y,String text){
		  Nai n=new Nai();
		  n.x=x;n.y=y;n.text=text;
		  nai.add(n);
		}
		public void adddan(float x,float y){
			Dan d=new Dan();
			d.x=x;d.y=y;d.r=20;
			dan.add(d);
		}
		public void addguai(float x,float y,float b)
		{Guai g=new Guai();
			g.x=x;g.y=y;g.b=b;g.r=40;
			guai.add(g);
		}
		public void logic()
		{   for(int a=0;a<guai.size();a++){for(int b=0;b<dan.size();b++){if(Math.abs(dan.get(b).x-guai.get(a).x)<dan.get(b).r+guai.get(a).r&&Math.abs(dan.get(b).y-guai.get(a).y)<dan.get(b).r+guai.get(a).r){dan.remove(b);guai.get(a).b-=1;}}}
			for(int a=0;a<guai.size();a++){guai.get(a).x-=1;if(guai.get(a).b<=0){guai.remove(a);sun+=30;}}
			for(int a=0;a<guai.size();a++){if(guai.get(a).x<10){isfail=true;ball.clear();guai.clear();dan.clear();nai.clear();}}
			for(int a=0;a<dan.size();a++){dan.get(a).x+=5;if(dan.get(a).x>Gdx.graphics.getWidth()){dan.remove(a);}}
			for(int b=0;b<nai.size();b++){nai.get(b).text=""+(int)ball.get(b).r;} 
			for(int a=0;a<guai.size();a++){for(int b=0;b<ball.size();b++){if(Math.abs(guai.get(a).x-ball.get(b).x)<guai.get(a).r+ball.get(b).r-5&&Math.abs(guai.get(a).y-ball.get(b).y)<guai.get(a).r+ball.get(b).r+5){ball.remove(b);nai.remove(b);}}}
			t+=Gdx.graphics.getDeltaTime();
			s+=Gdx.graphics.getDeltaTime();
			if(t>=0.6f){t=0;for(int a=0;a<ball.size();a++){ball.get(a).r-=1;if(ball.get(a).r<=0){ball.remove(a);nai.remove(a);}}for(int a=0;a<ball.size();a++){adddan(ball.get(a).x+ball.get(a).r,ball.get(a).y);}}
			if(s>=2f){s=0;addguai(Gdx.graphics.getWidth(),(float)Math.random()*(Gdx.graphics.getHeight()-200)+200,7);}
		}
	
	@Override
	public void dispose()
	{reastcamera();
	}

	@Override
	public void resize(int width, int height)
	{reastcamera();
	}

	@Override
	public void pause()
	{reastcamera();
	}

	@Override
	public void resume()
	{reastcamera();
	}
}
