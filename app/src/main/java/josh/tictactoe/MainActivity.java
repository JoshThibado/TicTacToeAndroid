package josh.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

boolean gameOn = true;
int turnNum = 1;

char[] BoardLayout = new char[9];//New Char for single array board layout

    //The char arrays below are for checking the win conditions
    char[] A = new char[3];
    char[] B = new char[3];
    char[] C = new char[3];


int width = 1080;//Default size, resized in the program
int height = 1920;//Default size, resized in the program

int rowSize = 550;//Default row size, resized in the program
int colSize = 360;//Default column size, resized in the program


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//Hides action bar

        TTTBoard();//Sets everything in board equal to space

        CustomDrawableView myCustomDrawableView = new CustomDrawableView(this);//Class that Draws the board and pieces
        setContentView(myCustomDrawableView);//Makes instance of above class
    }


    public class CustomDrawableView extends View {

        Paint paint = new Paint();//Paint for lines
        Paint paint2 = new Paint();//Paint for x's
        Paint paint3 = new Paint();//Paint for o's

        public CustomDrawableView(Context context) {
            super(context);
            paint.setColor(Color.BLACK);//Board lines black
            paint.setStrokeWidth(8f);//board lines 8f thick

            paint2.setColor(Color.RED);//X's red
            paint2.setStrokeWidth(16f);//x's 16f thick

            paint3.setColor(Color.BLUE);//o's blue
            paint3.setStrokeWidth(16f);//o's 16f thick
            paint3.setStyle(Paint.Style.STROKE);//Makes drawings non filled for O's
        }

        @Override
        protected void onDraw(Canvas canvas) {

            width = canvas.getWidth();//Gets width of phone screen, changes default value
            height = canvas.getHeight();//Gets height of phone screen, changes default value

            colSize = (canvas.getWidth()/3);//Sets column size to 1/3 width
            rowSize = (canvas.getHeight()/3);//Sets row size to 1/3 height

            canvas.drawLine(colSize, 0, colSize, height, paint); //Draw Vertical line 1
            canvas.drawLine(colSize*2, 0, colSize*2, height, paint); //Draw Vertical line 2
            canvas.drawLine(0, rowSize, width, rowSize, paint);  //Draw Horizontal line 1
            canvas.drawLine(0, rowSize*2, width, rowSize*2, paint);//Draw Horizontal line 2

            int indexPosY = 0;//Index posY, used to draw each piece's y position
            int indexPosX = 0;//Index posX, used to draw each pieces x position


            for(int j = 0; j <9;j++){
                //Display x or o at where that point in the array corresponds
                //Remember to use an algorithm that subtracts three or six for case: if (j >= 3 && j < 6) and case: if(j >=6 && j <9)

                indexPosX = getIndexAxisX(j);//Calls to get x index
                indexPosY = getIndexAxisY(j);//Calls to get y index

                RectF OvalShape = new RectF();//Makes new rectangleFloat that will be turned into oval "O"
                            OvalShape.set(

                            (colSize*indexPosX) + 10,//Left
                            ((indexPosY) * rowSize) +  10,//Top
                            ((indexPosX+1) * colSize) -  10,//Right
                            ((indexPosY + 1) * rowSize) - 10);//Bottom


                char bl = BoardLayout[j];
                switch(bl){
                    case 'x':
                        canvas.drawLine(// Draws the left hand side of cross: \
                                colSize*indexPosX + 10,//x start
                                ((indexPosY) * rowSize) +  10,//y start
                                colSize*(indexPosX+1) - 10,//x stop
                                ((indexPosY +1) * rowSize) - 10, paint2);//Y stop and calls x paint color

                        canvas.drawLine(// Draws the right hand side of cross: /
                                (colSize*(indexPosX+1)) - 10,//x start
                                ((indexPosY) * rowSize) +  10,//y start
                                (colSize*indexPosX) + 10,//x stop
                                ((indexPosY +1) * rowSize) - 10, paint2);//Y stop and calls x paint color
                        break;

                    case 'o':
                        canvas.drawOval(OvalShape, paint3);//Draws oval version of Rectangle float
                        break;


                }
            }
        }
    }

    //Listens for touch
    public boolean onTouchEvent(MotionEvent event){

        Context context = this;

        if (turnNum < 10 && gameOn) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //The if

                    if (x < colSize && y < rowSize && BoardLayout[0] == ' ') {
                        BoardLayout[0] = XorO();
                        turnNum++;
                    }
                    else if ((x > colSize) && (x < (colSize * 2)) && ((y < rowSize && BoardLayout[1] == ' '))) {
                        BoardLayout[1] = XorO();
                        turnNum++;
                    }
                    else if ((x > colSize * 2) && (x < (colSize * 3)) && ((y < rowSize && BoardLayout[2] == ' '))) {
                        BoardLayout[2] = XorO();
                        turnNum++;
                    }

                    else if (x < colSize && ((y > rowSize) && (y < rowSize * 2 && BoardLayout[3] == ' '))) {
                        BoardLayout[3] = XorO();
                        turnNum++;
                    }
                    else if ((x > colSize) && (x < (colSize * 2)) && ((y > rowSize) && (y < rowSize * 2 && BoardLayout[4] == ' '))) {
                        BoardLayout[4] = XorO();
                        turnNum++;
                    }
                    else if ((x > colSize * 2) && (x < (colSize * 3)) && ((y > rowSize) && (y < rowSize * 2 && BoardLayout[5] == ' '))) {
                        BoardLayout[5] = XorO();
                        turnNum++;
                    }

                    else if (x < colSize && ((y > rowSize * 2) && (y < rowSize * 3 && BoardLayout[6] == ' '))) {
                        BoardLayout[6] = XorO();
                        turnNum++;
                    }
                    else if ((x > colSize) && (x < (colSize * 2)) && ((y > rowSize * 2) && (y < rowSize * 3 && BoardLayout[7] == ' '))) {
                        BoardLayout[7] = XorO();
                        turnNum++;
                    }
                    else if ((x > colSize * 2) && (x < (colSize * 3)) && ((y > rowSize * 2) && (y < rowSize * 3 && BoardLayout[8] == ' '))) {
                        BoardLayout[8] = XorO();
                        turnNum++;
                    }

                    MediaPlayer popSound = MediaPlayer.create(context, R.raw.facebook_pop);//Media sound to play on touch
                    popSound.start();//Plays that sound ontouch

            }
        }
        CustomDrawableView myView = new CustomDrawableView(this);//Draws canvas again after touch
        setContentView(myView);//Sets view to this view ^^

        MediaPlayer winSound = MediaPlayer.create(context, R.raw.win_sound);//Sets winsound to play on win

        Intent i = getBaseContext().getPackageManager()//Intent to restart game after win follows
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        char winChar = Win();//Checks for win
        CharSequence textW =  winChar + " Is the Winner! Starting a new Game!";//Displays who won the game
        CharSequence textC =  "Cat's Game! Starting a new Game!";//If its a cats game displays this

        int duration = Toast.LENGTH_SHORT;//Totast duration short

        //The below are two if statments for if x (exclusive)or if o wins
        if(winChar == 'X' && gameOn){
            Toast toast = Toast.makeText(context, textW, duration);
            toast.show();
            gameOn = false;
            startActivity(i);
            winSound.start();
        }
        else if(winChar == 'O' && gameOn){
            Toast toast = Toast.makeText(context, textW, duration);
            toast.show();
            gameOn = false;
            startActivity(i);
            winSound.start();
        }

        else if(winChar == 'c' && gameOn){
            Toast toast = Toast.makeText(context, textC, duration);
            toast.show();
            gameOn = false;
            startActivity(i);
        }
        //return super.dispatchTouchEvent(event);
        return false;
    }

    //Method to return which piece to lay down or who laid down winning piece
    //Uses modulo to see if turn is even or odd/ O or X
    public char XorO(){

        char whichPlayer = ' ';

        if( turnNum%2 == 1) {
            whichPlayer = 'x';
        }
        else if(turnNum%2 == 0){
            whichPlayer = 'o';
        }
        return whichPlayer;//Returns which player it is
    }

    //Little method to return index pos for y axis
    public int getIndexAxisY(int j){
        int indexPosY = 0;
        if(j < 3){
            indexPosY = 0;
        }
        if(j > 2 && j < 6){
            indexPosY = 1;
        }
        if(j > 5){
            indexPosY = 2;
        }
        return indexPosY;
    }

    //Little method to return index pos for x axis
    public int getIndexAxisX(int j){
        int indexPosX = 0;

        if((j == 0) || (j - 3 == 0) || (j - 6 == 0)){
            indexPosX = 0;
        }
        if((j == 1) || (j - 3 == 1) || (j - 6 == 1)){
            indexPosX = 1;
        }
        if((j == 2) || (j - 3 == 2) || (j - 6 == 2)){
            indexPosX = 2;
        }
        return indexPosX;
    }

    //Clears tttboard/ puts all spaces in array
    public void TTTBoard(){
        for (int i = 0; i < 9; i++){
            BoardLayout[i] = ' ';
        }
    }

    //The following function checks for a winner after every turn
    public char Win(){

        boolean isWinner = false;//Is there a winner? No by default
        char Winner = ' ';//Winner set to blank

        //Converts array of 9 chars to three arrays of 3 chars each
        for(int i = 0; i < 9; i++){
            if(i < 3){
                A[i] = BoardLayout[i];
            }
            else if(i >= 3 && i < 6){
                B[i-3] = BoardLayout[i];
            }
            else if(i >= 6 && i < 9){
                C[i-6] = BoardLayout[i];
            }

        }

        //Checks vertical
        if(A[0] == A[1] && A[1] == A[2] && A[0] != ' '){
            isWinner = true;
        }
        else if(B[0] ==B[1] && B[1] == B[2] && B[0] != ' '){
            isWinner = true;
        }
        else if(C[0] ==C[1] && C[1] == C[2] && C[0] != ' '){
            isWinner = true;
        }

        ////Checks horizontal
        if(A[0] == B[0] && B[0] == C[0] && A[0] != ' '){
            isWinner = true;
        }
        else if(A[1] == B[1] && B[1] == C[1] && A[1] != ' '){
            isWinner = true;
        }
        else if(A[2] == B[2] && B[2] == C[2] && A[2] != ' '){
            isWinner = true;
        }

        //Checks diagonals
        if(A[0] == B[1] && B[1] == C[2] && A[0] != ' '){
            isWinner = true;
        }
        if(A[2] == B[1] && B[1] == C[0] && A[2] != ' '){
            isWinner = true;
        }

        if(isWinner){
            if(XorO() == 'x'){
                Winner = 'O';
            }
            else if(XorO() == 'o'){
                Winner = 'X';
            }
        }
        //If there isn't a winner by last turn's end, cats game
        if(!isWinner && turnNum == 10){
            Winner = 'c';
        }

        return Winner;
    }
}
