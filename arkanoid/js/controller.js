class Controller {
    constructor() {
        this.View = new View();
        this.Model = new Model();
    }

    initialize() {
        this.View.onKeyDownEvent = this.movePlatform.bind(this);
        this.View.onClickStartGameEvent = this.startGame.bind(this);
        this.View.init();

        this.initPlatform();
        this.initBall();
    }

    initPlatform() {
        let platform = this.View.getPlatformCoordinates();
        this.Model.initPlatform(this.View.widthField, this.View.heightField, platform.height, platform.width);
        this.View.initPlatform(this.Model.objects.platform.x, this.Model.objects.platform.y);
    }

    initBall() {
        let ball = this.View.getBallCoordinates();
        this.Model.initBall(this.View.widthField, this.View.heightField, ball.height, ball.width);
        this.View.initBall(this.Model.objects.ball.x, this.Model.objects.ball.y);
    }

    createSquares() {
        let numberSquares = this.View.getUserInput().numberSquares;
        this.Model.createSquares(numberSquares);
        this.View.renderSquares(this.Model.objects.squares);
    }

    movePlatform(e) {
        this.Model.movePlatform(e);
        this.View.renderPlatform(this.Model.objects.platform);
    }

    startGame() {
        this.clear();
        this.createSquares();
        this.animateBall();
    }

    animateBall() {
        this.startGame = this.startGame.bind(this);
        requestAnimationFrame(this.startGame);
        this.Model.moveBall();
        this.View.renderBall(this.Model.objects.ball);
    }

    clear() {
        this.View.clear();
    }
}

let controller = new Controller();
controller.initialize();
