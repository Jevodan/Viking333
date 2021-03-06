package ru.jevo.animation.sprites.ships;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.jevo.animation.Weapons;
import ru.jevo.animation.basic.Pool;
import ru.jevo.animation.basic.Ship;
import ru.jevo.animation.basic.Weapon;
import ru.jevo.animation.pools.weapons.BulletPool;
import ru.jevo.animation.service.Rect;
import ru.jevo.animation.sprites.weapon.Bullet;
import ru.jevo.animation.sprites.weapon.Laser;
import ru.jevo.animation.sprites.weapon.Mega;

/**
 * Created by Alexander on 03.12.2018.
 */
public class Viking extends Ship {

    public static final int DAMAGE_TARAN = 100;

    private boolean pressedLeft;
    private boolean pressedRight;
    private TextureAtlas atlas;

    private int hP = 100;

    public Viking(TextureAtlas atlas, String weapon) {
        super(atlas.findRegion("viking"), 1, 2, 2);
        this.weaponEnum = weapon;
        setHeightProportion(1f);
        setAngle(0);
        speedVector.set(1.5f, 0);
        createWeapon(weapon);
        this.atlas = atlas;

    }


    private void createWeapon(String item) {
        if (item.equals("bullet")) {
            weaponPool = BulletPool.getInstance();
        } else if (item.equals("blaster")) {
            weaponPool = BulletPool.getInstance();
        } else if (item.equals("mega")) {
            weaponPool = BulletPool.getInstance();
        } else if (item.equals("bullet")) {
            weaponPool = BulletPool.getInstance();
        }
    }


    @Override
    public void resize(Rect serviceRect) {
        super.resize(serviceRect);
        System.out.println(mServiceRect.getHeight() + "????");
        pos.set(0, -mServiceRect.getHalfHeight() + this.getHalfHeight());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        check();
        speedFire += delta;
        pos.mulAdd(speed, delta); //скорость привязана к частоте кадров
    }

    private void check() {
        if (pos.x + this.getHalfWidth() > mServiceRect.getHalfWidth()) {
            stop();
            pos.x -= 0.01f;
        }
        if (pos.x - this.getHalfWidth() < -mServiceRect.getHalfWidth()) {
            stop();
            pos.x += 0.01f;
        }

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pos.x > touch.x) goLeft();
        if (pos.x < touch.x) goRight();
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);

    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                if (pressedLeft)
                    goLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                if (pressedRight)
                    goRight();
                break;
        }

        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight)
                    goRight();
                else
                    stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft)
                    goLeft();
                else
                    stop();
                break;
            case Input.Keys.UP:
                if (speedFire > 1f) {
                    shoot();
                    speedFire = 0;
                }
                break;
        }
        return false;
    }

    private void stop() {
        speed.setZero();
    }

    private void goRight() {
        speed.set(speedVector);
        //currentFrame = 0;
    }

    private void goLeft() {
        speed.set(speedVector).rotate(180);
        System.out.println(mServiceRect.getHalfWidth());
        //currentFrame = 1;
    }

    private void shoot() {
        System.out.println("огонь");

        float angleRot = -90f;
        for (int i = 0; i < 3; i++) {
            weapon = (Weapon) weaponPool.obtain();
            //    Weapon bullet = bulletPool.obtain();
            angleRot += 45f;
            System.out.println("Угол:" + angleRot);
            weapon.setSpeedBul(weapon.getSpeedBul().cpy().rotate(angleRot));
            weapon.set(this, atlas.findRegion("bulletMainShip"), 0.1f, mServiceRect, false, 5);
        }

    }

    public int gethP() {
        return hP;
    }

    public void sethP(int hP) {
        this.hP = hP;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }


    @Override
    protected TextureRegion getRegion() {
        return this.enemyTextureAtlas.findRegion("viking");
    }


}
