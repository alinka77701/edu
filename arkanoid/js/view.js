class View {

    constructor() {
        this.heightField = 700;
        this.widthField = 1000;
        this.onKeyDownEvent = null;
        this.onClickStartGameEvent = null;
        this.platform = document.getElementById("platform");
        this.ball = document.getElementById("ball");
    }

    init() {
        document.addEventListener('keydown', this.onKeyDownEvent);
        document.getElementById("start").addEventListener('click', this.onClickStartGameEvent);
    }


    initPlatform(x, y) {
        this.platform.style.top = y + "px";
        this.platform.style.left = x + "px";
    }

    initBall(x, y) {
        this.ball.style.top = y + "px";
        this.ball.style.left = x + "px";
    }

    getPlatformCoordinates() {
        let heightPlatform = this.platform.clientHeight;
        let widthPlatform = this.platform.clientWidth;

        return {
            height: heightPlatform,
            width: widthPlatform
        };
    }

    getBallCoordinates() {
        let heightBall = this.ball.clientHeight;
        let widthBall = this.ball.clientWidth;

        return {
            height: heightBall,
            width: widthBall
        };
    }

    renderPlatform(object) {
        this.platform.style.left = object.x + 'px';
        this.platform.style.top = object.y + 'px';
    }

    renderBall(object) {
        this.ball.style.left = object.x + 'px';
        this.ball.style.top = object.y + 'px';
        /*if(object.isDied===true){
            this.ball.setAttribute('hidden', 'true');
        }*/
    }

    renderSquares(squares) {
        let squareContainer = document.getElementById("square-container");
        for (let i = 0; i < squares.length; i++) {
            let square = document.createElement("div");
            square.setAttribute("class", "square");
            square.style.top = squares[i].y + 'px';
            square.style.left = squares[i].x + 'px';
            squareContainer.appendChild(square);
        }
    }

    getUserInput() {
        let numberSquares = document.getElementById("number-squares").value;
        return {
            numberSquares: numberSquares
        };
    }

    clear(){
        let squareContainer = document.getElementById("square-container");
        while (squareContainer.firstChild) {
            squareContainer.removeChild(squareContainer.firstChild);
        }


    }
}
