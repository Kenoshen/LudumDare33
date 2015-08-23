package ludum.dare.world;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.Conf;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.CollisionCallback;
import ludum.dare.utils.HealthCallback;
import ludum.dare.utils.NamedAnimation;


/**
 * Created by mwingfield on 8/3/15.
 */
public class Player extends GameObject {

    private PhysicalTrait physical;
    private AnimatorTrait animator;
    private TimedCollisionTrait hitboxes;
    public boolean rightFacing = true;
    public boolean hitFromRight;

    private CollisionCallback collisionFunc = new CollisionCallback() {
            @Override
        public void collide(GameObject obj) {
                HealthTrait health = obj.getTrait(HealthTrait.class);
                if (health != null) {
                    health.damage(10, Player.this);
                    System.out.println("I doth remain standing.");
                }
            System.out.println("Those are my cans");
            SoundLibrary.GetSound("Hit_Robot").play();
        }
    };

    private HealthCallback healthCallback = new HealthCallback() {
        @Override
        public void damageReceived(int amount, GameObject from) {

        }

        @Override
        public void healthRegained(int amount, GameObject from) {

        }
    };

    public Player(float x, float y, float z, float width, float height, CMouse mouse, CKeyboard keyboard, CGamePad gamepad){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));
        traits.add(new CollidableTrait(this, collisionFunc));
        traits.add(new ImmobilizedTrait(this));

        traits.add(new HealthTrait(this, 100, healthCallback));

        ID = "Player";


        AnimationBundle bundle = new AnimationBundle();
        bundle.addNamedAnimation(new NamedAnimation("stand", 0.1f,
                AtlasManager.instance.getAtlas("bum").findRegions("stand/bumStand"), AtlasManager.instance.getAtlas("bum_n").findRegions("stand/bumStand_n"),
                new Vector2(0, 0), new Vector2(width, height)));
        bundle.addNamedAnimation(new NamedAnimation("pain", .15f,
                AtlasManager.instance.getAtlas("bum").findRegions("pain/bumPain"), AtlasManager.instance.getAtlas("bum").findRegions("pain/bumPain"),
                new Vector2(0, 0f), new Vector2(width, height)));
        bundle.addNamedAnimation(new NamedAnimation("backpain", .15f,
                AtlasManager.instance.getAtlas("bum").findRegions("backpain/bumBackPain"), AtlasManager.instance.getAtlas("bum").findRegions("backpain/bumBackPain"),
                new Vector2(0, 0f), new Vector2(width, height)));
        CollisionSequence standSequence = new CollisionSequence();
        standSequence.name = "stand";

        CollisionGroup standGroup = new CollisionGroup();
        standGroup.boxes = new Rectangle[] {new Rectangle(-3.5f, -4.5f, 4.5f, 6)};
        standGroup.circles = new Circle[] {new Circle(.5f, 2.5f, 2)};

        standSequence.frames = new CollisionGroup[9];
        for(int i = 0; i < standSequence.frames.length; i++){
            standSequence.frames[i] = standGroup;
        }
        bundle.addHurtboxSequence(standSequence);

        bundle.addNamedAnimation(new NamedAnimation("walk", 0.1f,
                AtlasManager.instance.getAtlas("bum").findRegions("walk/bumWalk"), AtlasManager.instance.getAtlas("bum_n").findRegions("walk/bumWalk_n"),
                new Vector2(0, 0), new Vector2(width, height)));
        CollisionSequence walkSequence = new CollisionSequence();
        walkSequence.name = "walk";

        CollisionGroup walkGroup = new CollisionGroup();
        walkGroup.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        walkGroup.circles = new Circle[] {new Circle(.5f, 3, 2)};

        walkSequence.frames = new CollisionGroup[8];
        for(int i = 0; i < walkSequence.frames.length; i++){
            walkSequence.frames[i] = walkGroup;
        }
        bundle.addHurtboxSequence(walkSequence);


        bundle.addNamedAnimation(new NamedAnimation("punch", 0.1f,
                AtlasManager.instance.getAtlas("bum").findRegions("jab/bumJab"), AtlasManager.instance.getAtlas("bum_n").findRegions("jab/bumJab_n"),
                new Vector2(1.7f, 0), new Vector2(width * 1.25f, height)));
        CollisionSequence punchHurtSequence = new CollisionSequence();
        punchHurtSequence.name = "punch";

        CollisionGroup punchBodyGroup1 = new CollisionGroup();
        punchBodyGroup1.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        punchBodyGroup1.circles = new Circle[] {new Circle(2.5f, 1.5f, 2)};

        CollisionGroup punchBodyGroup2 = new CollisionGroup();
        punchBodyGroup2.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        punchBodyGroup2.circles = new Circle[] {new Circle(1.5f, 2.5f, 2)};

        CollisionGroup punchBodyGroup3 = new CollisionGroup();
        punchBodyGroup3.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        punchBodyGroup3.circles = new Circle[] {new Circle(.5f, 3, 2)};

        punchHurtSequence.frames = new CollisionGroup[] {punchBodyGroup1, punchBodyGroup2, punchBodyGroup3};
        bundle.addHurtboxSequence(punchHurtSequence);

        CollisionSequence jabHitSequence = new CollisionSequence();
        jabHitSequence.name = "punch";

        CollisionGroup group1 = new CollisionGroup();
        group1.circles = new Circle[] {new Circle(7.2f,.5f,1.5f)};

        jabHitSequence.frames = new CollisionGroup[3];
        jabHitSequence.frames[0] = group1;
        bundle.addHitboxSequence(jabHitSequence);


        bundle.addNamedAnimation(new NamedAnimation("punch2", 0.1f,
                AtlasManager.instance.getAtlas("bum").findRegions("cross/bumCross"), AtlasManager.instance.getAtlas("bum_n").findRegions("cross/bumCross_n"),
                new Vector2(1.7f, 0), new Vector2(width * 1.25f, height)));
        CollisionSequence crossHurtSequence = new CollisionSequence();
        crossHurtSequence.name = "punch2";

        CollisionGroup crossBodyGroup0 = new CollisionGroup();
        crossBodyGroup0.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        crossBodyGroup0.circles = new Circle[] {new Circle(1.25f, 2.75f, 2)};

        CollisionGroup crossBodyGroup1 = new CollisionGroup();
        crossBodyGroup1.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        crossBodyGroup1.circles = new Circle[] {new Circle(2.75f, 2.75f, 2)};

        CollisionGroup crossBodyGroup2 = new CollisionGroup();
        crossBodyGroup2.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        crossBodyGroup2.circles = new Circle[] {new Circle(1.75f, 2.75f, 2)};

        CollisionGroup crossBodyGroup3 = new CollisionGroup();
        crossBodyGroup3.boxes = new Rectangle[] {new Rectangle(-3f, -4.5f, 4f, 6)};
        crossBodyGroup3.circles = new Circle[] {new Circle(.5f, 2.5f, 2)};

        crossHurtSequence.frames = new CollisionGroup[] {crossBodyGroup0, crossBodyGroup1, crossBodyGroup2, crossBodyGroup2, crossBodyGroup3};
        bundle.addHurtboxSequence(crossHurtSequence);

        CollisionSequence crossSequence = new CollisionSequence();
        crossSequence.name = "punch2";

        CollisionGroup group2 = new CollisionGroup();
        group2.circles = new Circle[] {new Circle(8f,1.5f,2f)};

        crossSequence.frames = new CollisionGroup[5];
        crossSequence.frames[1] = group2;
        bundle.addHitboxSequence(crossSequence);

        bundle.addNamedAnimation(new NamedAnimation("jump", .1f,
                AtlasManager.instance.getAtlas("bum").findRegions("jump/bumJump"), AtlasManager.instance.getAtlas("bum_n").findRegions("cross/bumCross_n"),
                new Vector2(0, 1.75f), new Vector2(width, height * 1.25f)));
        CollisionSequence jumpSequence = new CollisionSequence();
        jumpSequence.name = "jump";

        CollisionGroup jumpGroup1 = new CollisionGroup();
        jumpGroup1.boxes = new Rectangle[] {new Rectangle(-4f, -2f, 3.5f, 5)};
        jumpGroup1.circles = new Circle[] {new Circle(.25f, 3.75f, 2)};

        CollisionGroup jumpGroup2 = new CollisionGroup();
        jumpGroup2.boxes = new Rectangle[] {new Rectangle(-4f, 0, 3.5f, 5)};
        jumpGroup2.circles = new Circle[] {new Circle(.25f, 5.75f, 2)};

        CollisionGroup jumpGroup3 = new CollisionGroup();
        jumpGroup3.boxes = new Rectangle[] {new Rectangle(-3.5f, .5f, 3.5f, 5)};
        jumpGroup3.circles = new Circle[] {new Circle(.75f, 6.75f, 2)};

        CollisionGroup jumpGroup4 = new CollisionGroup();
        jumpGroup4.boxes = new Rectangle[] {new Rectangle(-3.5f, .5f, 3.5f, 5)};
        jumpGroup4.circles = new Circle[] {new Circle(.5f, 6.75f, 2)};

        CollisionGroup jumpGroup5 = new CollisionGroup();
        jumpGroup5.boxes = new Rectangle[] {new Rectangle(-3.5f, -2f, 3.5f, 5)};
        jumpGroup5.circles = new Circle[] {new Circle(.5f, 4.25f, 2)};

        jumpSequence.frames = new CollisionGroup[] {jumpGroup1, jumpGroup2, jumpGroup3, jumpGroup4, jumpGroup5};
        bundle.addHurtboxSequence(jumpSequence);

        bundle.addNamedAnimation(new NamedAnimation("jumpKick", .1f,
                AtlasManager.instance.getAtlas("bum").findRegions("kick/bumKick"), AtlasManager.instance.getAtlas("bum_n").findRegions("cross/bumCross_n"),
                new Vector2(0, 1.75f), new Vector2(width, height * 1.25f)));
        CollisionSequence jumpkickSequence = new CollisionSequence();
        jumpkickSequence.name = "jumpKick";

        CollisionGroup jumpkickGroup1 = new CollisionGroup();
        jumpkickGroup1.boxes = new Rectangle[] {new Rectangle(-3f, -2f, 3.5f, 5)};
        jumpkickGroup1.circles = new Circle[] {new Circle(-.25f, 3.75f, 2)};

        CollisionGroup jumpkickGroup2 = new CollisionGroup();
        jumpkickGroup2.boxes = new Rectangle[] {new Rectangle(-3f, 0f, 3.5f, 5)};
        jumpkickGroup2.circles = new Circle[] {new Circle(-.25f, 5.75f, 2)};

        CollisionGroup jumpkickGroup3 = new CollisionGroup();
        jumpkickGroup3.boxes = new Rectangle[] {new Rectangle(-3f, .5f, 3.5f, 5)};
        jumpkickGroup3.circles = new Circle[] {new Circle(-.25f, 6.25f, 2)};

        CollisionGroup jumpkickGroup4 = new CollisionGroup();
        jumpkickGroup4.boxes = new Rectangle[] {new Rectangle(-3.5f, .5f, 3.5f, 5)};
        jumpkickGroup4.circles = new Circle[] {new Circle(.5f, 6.75f, 2)};

        CollisionGroup jumpkickGroup5 = new CollisionGroup();
        jumpkickGroup5.boxes = new Rectangle[] {new Rectangle(-3.5f, -2f, 3.5f, 5)};
        jumpkickGroup5.circles = new Circle[] {new Circle(.5f, 4.25f, 2)};

        CollisionGroup jumpkickGroup6 = new CollisionGroup();
        jumpkickGroup6.boxes = new Rectangle[] {new Rectangle(-3f, -2f, 3.5f, 5)};
        jumpkickGroup6.circles = new Circle[] {new Circle(-.25f, 3.75f, 2)};

        jumpkickSequence.frames = new CollisionGroup[] {jumpkickGroup1, jumpkickGroup2, jumpkickGroup3, jumpkickGroup3, jumpkickGroup3, jumpkickGroup6};
        bundle.addHurtboxSequence(jumpkickSequence);

        CollisionSequence jumpkickHitboxes = new CollisionSequence();
        jumpkickHitboxes.name = "jumpKick";

        CollisionGroup kickBox1 = new CollisionGroup();
        kickBox1.circles = new Circle[] { new Circle(4, -1, 2), new Circle(4, 2, 2)};

        CollisionGroup kickBox2 = new CollisionGroup();
        kickBox2.circles = new Circle[] { new Circle(4, 1, 2), new Circle(4, 4, 2)};

        CollisionGroup kickBox3 = new CollisionGroup();
        kickBox3.circles = new Circle[] { new Circle(4, 1.5f, 2), new Circle(4, 4.5f, 2)};

        CollisionGroup kickBox4 = new CollisionGroup();
        kickBox4.circles = new Circle[] { new Circle(4, 1f, 2), new Circle(4, 4f, 2)};

        CollisionGroup kickBox5 = new CollisionGroup();
        kickBox5.circles = new Circle[] { new Circle(4, -1, 2), new Circle(4, 2f, 2)};

        jumpkickHitboxes.frames = new CollisionGroup[] {kickBox1, kickBox2, kickBox3, kickBox3, kickBox4, kickBox5};
        bundle.addHitboxSequence(jumpkickHitboxes);


        bundle.addNamedAnimation(new NamedAnimation("land", .1f,
                AtlasManager.instance.getAtlas("bum").findRegions("land/bumLand"), AtlasManager.instance.getAtlas("bum_n").findRegions("cross/bumCross_n"),
                new Vector2(0, 1.75f), new Vector2(width, height * 1.25f)));
        CollisionSequence landSequence = new CollisionSequence();
        landSequence.name = "land";

        CollisionGroup landSeq1 = new CollisionGroup();
        landSeq1.boxes = new Rectangle[] {new Rectangle(-3.5f, -4f, 3.5f, 5)};
        landSeq1.circles = new Circle[] {new Circle(.5f, 2.25f, 2)};

        CollisionGroup landSeq2 = new CollisionGroup();
        landSeq2.boxes = new Rectangle[] {new Rectangle(-3.5f, -4f, 3.5f, 5)};
        landSeq2.circles = new Circle[] {new Circle(1, 1f, 2)};

        CollisionGroup landSeq3 = new CollisionGroup();
        landSeq3.boxes = new Rectangle[] {new Rectangle(-3.5f, -4f, 3.5f, 5)};
        landSeq3.circles = new Circle[] {new Circle(.75f, 1.5f, 2)};

        landSequence.frames = new CollisionGroup[] {landSeq1, landSeq2, landSeq3};
        bundle.addHurtboxSequence(landSequence);


        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        hitboxes = new TimedCollisionTrait(this, bundle);
        traits.add(hitboxes);

        traits.add(new InputHandlerTrait(this, mouse, keyboard, gamepad));
        traits.add(new ControlTrait(this));
        //
        // this part sets the collision filter for the boundary object
        // all boundary objects collide with Actor objects
        FixtureDef fD = new FixtureDef();
        fD.filter.categoryBits = Conf.instance.physicsBitFilterActor();
        fD.filter.maskBits = Conf.instance.physicsBitFilterBoundary();
        //
        BodyDef bD = new BodyDef();
        bD.type = BodyDef.BodyType.DynamicBody;
        bD.position.x = x;
        bD.position.y = y;
        CBody body = new BoxBody(width/2, 1).init(fD, bD);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, height/2 - .5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();

        body.setFriction(1);
    }

    public void collidedWith(GameObject o) {
        Vector2 v = new Vector2(0,0);
        ControlTrait myControl = getTrait(ControlTrait.class);
        if(o instanceof EnemyBasic){

            SoundLibrary.GetSound("Get_Hit").play();

            if (getTrait(PositionTrait.class).x < o.getTrait(PositionTrait.class).x
                    && getTrait(PositionTrait.class).y < o.getTrait(PositionTrait.class).y){
                getTrait(ImmobilizedTrait.class).imob = true;
                hitFromRight = true;
                myControl.queuedAttack = false;
                myControl.attacking = false;
                myControl.jumping = false;
                myControl.landing = false;
                v.x -= 10000;
                v.y -= 50;
            }
            if (getTrait(PositionTrait.class).x >= o.getTrait(PositionTrait.class).x
                    && getTrait(PositionTrait.class).y < o.getTrait(PositionTrait.class).y){
                getTrait(ImmobilizedTrait.class).imob = true;
                hitFromRight = false;
                myControl.queuedAttack = false;
                myControl.attacking = false;
                myControl.jumping = false;
                myControl.landing = false;
                v.x += 10000;
                v.y -= 50;
            }
            if (getTrait(PositionTrait.class).x >= o.getTrait(PositionTrait.class).x
                    && getTrait(PositionTrait.class).y >= o.getTrait(PositionTrait.class).y){
                getTrait(ImmobilizedTrait.class).imob = true;
                hitFromRight = false;
                myControl.queuedAttack = false;
                myControl.attacking = false;
                myControl.jumping = false;
                myControl.landing = false;
                v.x += 10000;
                v.y += 50;
            }
            if (getTrait(PositionTrait.class).x < o.getTrait(PositionTrait.class).x
                    && getTrait(PositionTrait.class).y >= o.getTrait(PositionTrait.class).y){
                getTrait(ImmobilizedTrait.class).imob = true;
                hitFromRight = true;
                myControl.queuedAttack = false;
                myControl.attacking = false;
                myControl.jumping = false;
                myControl.landing = false;
                v.x -= 10000;
                v.y += 50;
            }
            getTrait(PhysicalTrait.class).body.body.setLinearVelocity(v);
        }
    }
}