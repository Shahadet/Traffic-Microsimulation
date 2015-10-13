package com.shahadet.simulator1;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static utils.Constants.PPM;
public class TMS1 extends ApplicationAdapter {

	private final float SCALE = 2.0f;
	private ShapeRenderer shapeRenderer; 
	private OrthographicCamera camera;
	private Box2DDebugRenderer b2dr;
	private World world;
	private Body car1,car2;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		System.out.println(w+"  "+h);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/SCALE, h/SCALE);
		world = new World(new Vector2(0, 0f), false);
		b2dr = new Box2DDebugRenderer();
		car1 = createCar(0,0,10,10, false);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//System.out.println("Car position"+car1.getPosition().x + " "+car1.getPosition().y);
		
		b2dr.render(world, camera.combined.scl(PPM));
		shapeRenderer = new ShapeRenderer();
		
		shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.line(0,260,720,260);//upper line
        shapeRenderer.line(0,240,720,240);//middle line
        shapeRenderer.line(0,220,720,220);//lower line
		shapeRenderer.end();
	}
	
	@Override
	public void resize(int width,int height){
		camera.setToOrtho(false, width/SCALE, height/SCALE);
	}
	
	public void update(float delta)
	{
		world.step(1/60f, 6,2);
		//inputUpdate(delta);
		cameraUpdate(delta);
	}
	
	public void cameraUpdate(float delta){
		Vector3 position = camera.position;
		position.x = 0;	//setting the camera to the center of the player
		position.y = 0;
		camera.position.set(position);
		
		camera.update();
		
	}
	public Body createCar(int x,int y,int width,int height,boolean isStatic){
		Body pBody;
		BodyDef def = new BodyDef();
		if(isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;
		//def.position.set(x/PPM,y/PPM);
		//def.fixedRotation = true;
		def.position.set(x,y);
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
 		shape.setAsBox(width/2/PPM, height/2/PPM);
		//shape.setAsBox(width/2, height/2);
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		return pBody;
	}
	@Override
	public void dispose() {
		world.dispose();
		b2dr.dispose();
	}
}
