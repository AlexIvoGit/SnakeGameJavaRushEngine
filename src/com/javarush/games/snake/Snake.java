package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {
        super(x, y);
        GameObject one = new GameObject(x, y);
        GameObject two = new GameObject(x + 1, y);
        GameObject three = new GameObject(x + 2, y);
        snakeParts.add(one);
        snakeParts.add(two);
        snakeParts.add(three);
    }


    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public int getLength() {
        return snakeParts.size();
    }

    public void setDirection(Direction direction) {
        switch (this.direction) {
            case LEFT:
                if (direction == Direction.RIGHT || snakeParts.get(0).x == snakeParts.get(1).x) break;
                this.direction = direction;
            case RIGHT:
                if (direction == Direction.LEFT || snakeParts.get(0).x == snakeParts.get(1).x) break;
                this.direction = direction;
            case UP:
                if (direction == Direction.DOWN || snakeParts.get(0).y == snakeParts.get(1).y) break;
                this.direction = direction;
            case DOWN:
                if (direction == Direction.UP || snakeParts.get(0).y == snakeParts.get(1).y) break;
                this.direction = direction;
        }
    }

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    public void draw(Game game) {
        if (isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
        } else {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
        }
        for (int i = 1; i < snakeParts.size(); i++) {
            if (isAlive) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
            } else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }

        }
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }

        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        } else snakeParts.add(0, newHead);

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }

    }

    public GameObject createNewHead() {
        switch (direction) {
            case LEFT:
                return new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
            case UP:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
            case DOWN:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
            case RIGHT:
                return new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        }
        return null;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject object) {
        boolean result = false;
        for (int i = 0; i < snakeParts.size(); i++) {
            if (object.x == snakeParts.get(i).x && object.y == snakeParts.get(i).y) {
                result = true;
            }
        }
        return result;
    }
}
