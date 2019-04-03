const DISTANCE_FROM_BOTTOM_TO_PLATFORM = 20;
const DISTANCE_FROM_BOTTOM_TO_BALL = 40;
const KEY_CODE_RIGHT = 39;
const KEY_CODE_LEFT = 37;
const PLATFORM_STEP = 20;
const LEFT_SCREEN_BORDER = 0;
const RIGHT_SCREEN_BORDER = 850;
const TOP_SCREEN_BORDER = 0;
const BOTTOM_SCREEN_BORDER = 680;
const WIDTH_GAME_FIELD = 1001;
const BALL_STEP_X = 2;
const BALL_STEP_Y = 4;

class Platform {
    constructor(height, width) {
        this.x = 0;
        this.y = 0;

        this.height = height;
        this.width = width;
    }

    setCoordinates(x, y) {
        this.y = y;
        this.x = x;
    }
}

class Ball {
    constructor(height, width) {
        this.x = 0;
        this.y = 0;
        this.dy = 1;
        this.dx = 1;
        this.r = 15;
        this.height = height;
        this.width = width;
        this.isDied=false;
    }
}

class Square {
    constructor(height, width) {
        this.x = 0;
        this.y = 0;

        this.height = height;
        this.width = width;
    }
}

class Model {

    constructor() {
        this.objects = {
            platform: new Platform(0, 0),
            ball: new Ball(0, 0)
        };
    }


    initPlatform(widthField, heightField, heightPlatform, widthPlatform) {
        this.objects.platform.height = heightPlatform;
        this.objects.platform.width = widthPlatform;

        this.objects.platform.x = (widthField / 2) - (this.objects.platform.width / 2);
        this.objects.platform.y = heightField - this.objects.platform.height - DISTANCE_FROM_BOTTOM_TO_PLATFORM;
    }

    initBall(widthField, heightField, heightBall, widthBall) {
        this.objects.ball.height = heightBall;
        this.objects.ball.width = widthBall;

        this.objects.ball.x = (widthField / 2) - (this.objects.ball.width / 2);
        this.objects.ball.y = 300;
    }

    movePlatform(event) {
        let x = 0, y = this.objects.platform.y, keyCode = event.keyCode;
        switch (keyCode) {
            case KEY_CODE_LEFT:
                x = this.objects.platform.x - PLATFORM_STEP;
                break;
            case KEY_CODE_RIGHT:
                x = this.objects.platform.x + PLATFORM_STEP;
                break;

            default:
                x = this.objects.platform.x;
                y = this.objects.platform.y;
                break;
        }

        let coordinates = this.checkScreenBordersPlatform(x, y);
        this.objects.platform.setCoordinates(coordinates.x, coordinates.y);
    }

    checkScreenBordersPlatform(x, y) {
        if (x < LEFT_SCREEN_BORDER)
            x = LEFT_SCREEN_BORDER;
        if (x > RIGHT_SCREEN_BORDER)
            x = RIGHT_SCREEN_BORDER;
        if (y < TOP_SCREEN_BORDER)
            x = TOP_SCREEN_BORDER;
        if (y > BOTTOM_SCREEN_BORDER)
            y = BOTTOM_SCREEN_BORDER;
        return {
            x: x,
            y: y
        }
    }


    moveBall() {
        if (this.objects.ball.dy === 1)
            this.objects.ball.y += BALL_STEP_Y;
        else
            this.objects.ball.y -= BALL_STEP_Y;

        if (this.objects.ball.y >= BOTTOM_SCREEN_BORDER - (this.objects.ball.r + BALL_STEP_Y))
        {
            this.objects.ball.dy *= -1;
            this.objects.ball.isDied=true;
        }

        if (this.objects.ball.y <= TOP_SCREEN_BORDER + (this.objects.ball.r + BALL_STEP_Y))
            this.objects.ball.dy *= -1;


        if (this.objects.ball.dx === 1)
            this.objects.ball.x += BALL_STEP_X;
        else
            this.objects.ball.x -= BALL_STEP_X;

        if (this.objects.ball.x >= LEFT_SCREEN_BORDER + (this.objects.ball.r + BALL_STEP_X))
            this.objects.ball.dx *= -1;
        if (this.objects.ball.x <= RIGHT_SCREEN_BORDER - (this.objects.ball.r + BALL_STEP_X))
            this.objects.ball.dx *= -1;

        if(this.objects.ball.x>800)
            debugger;

    }

    createSquares(numberSquares) {
        let squares = [];
        let square = new Square(40, 40);
        square.x = 1;
        square.y = 1;
        squares.push(square);
        for (let i = 1; i < numberSquares; i++) {
            let square = new Square(40, 40);
            square.x = squares[i - 1].x + square.width;
            square.y = squares[i - 1].y;
            if (square.x / WIDTH_GAME_FIELD === 1) {
                square.x = 1;
                square.y = squares[i - 1].y + square.height;
            }
            squares.push(square);
        }
        this.objects.squares = squares;
    }
}