package com.Chess;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

/**
 * compute all legal moves for a piece on a given chessboard
 */
final class ChessMove {

    /**
     * chess board 2d array
     */
    private Piece[][] mPBoard;
    private Piece mPiece;
    private List<String> mLegalMoves;
    private static final int CHESS_LENGTH = 8;
    private static final int LAST_SQUARE_INDEX =7;
    private static final int ROW_LIST_CHECK = 3;
    private static final int SECOND_LAST_INDEX = 6;
     ChessMove(){

        mPBoard = new Piece[CHESS_LENGTH][CHESS_LENGTH];
        mLegalMoves = new ArrayList<>();
    }

    public void readInput(final String inputstr){

        final String[] rowlst = inputstr.split("\n");
        if(rowlst.length != ROW_LIST_CHECK){
            throw new ArrayIndexOutOfBoundsException("Input should have three lines.");
        }

        for(int i = 0 ; i < 2 ; i++){
            final String[] rowline = rowlst[i].trim().split(":");
            if(rowline.length != 2){
                throw new IllegalArgumentException("Input line format is invalid.");
            }
            final String side = rowline[0];
            if(!"BLACK".equals(side) && !"WHITE".equals(side)){
                throw new IllegalArgumentException("Input lines should start with 'BLACK' or 'WHITE'.");
            }
            boolean bwhite = true;
            if("BLACK".equals(side)) {
                bwhite = false;
            }
            final String[] arrPiece = rowline[1].split(",");
            for(final String piece : arrPiece){
                final Piece curPiece = new Piece(piece.trim(), bwhite);
                if(!isValidPiece(curPiece, ' ')){
                    throw new IllegalArgumentException("Piece format of input line is invalid.");
                }
                mPBoard[curPiece.posX][curPiece.posY] = curPiece;
            }
        }
        final String[] prow = rowlst[2].trim().split(":");
        if(prow.length != 2){
            throw new IllegalArgumentException("Line format of moving piece is invalid.");
        }
        if(!"PIECE TO MOVE".equals(prow[0].trim())){
            throw new IllegalArgumentException("Line format of moving should start with 'PIECE TO MOVE'.");
        }
        mPiece = new Piece(prow[1].trim(), false);
        if(!isValidPiece(mPiece, mPiece.name)){
            throw new IllegalArgumentException("The name of moveing piece does not match with board.");
        }
        mPiece.bWhite = mPBoard[mPiece.posX][mPiece.posY].bWhite;

    }

    public void clearBoard(){
        mPBoard = new Piece[CHESS_LENGTH][CHESS_LENGTH];
        mLegalMoves = new ArrayList<>();
    }

    public String movePiece(){
        switch (mPiece.name) {
            case 'K':
                moveKing();
                break;
            case 'Q':
                moveQueen();
                break;
            case 'R':
                moveRook();
                break;
            case 'B':
                moveBishop();
                break;
            case 'N':
                moveKnight();
                break;
            case 'P':
                movePawn();
                break;
            default:
                break;
        }
        String result = "LEGAL MOVES FOR " + mPiece.getPiece() + ": ";
        result += String.join(", ", mLegalMoves);
        return result;
    }
    private boolean isValidPiece(final Piece curPiece, final char place){
        if(!"KQRBNP".contains(String.valueOf(curPiece.name))){
            return false;
        }
        if(curPiece.posX < 0 || curPiece.posX > LAST_SQUARE_INDEX) {
            return false;
        }
        if(curPiece.posY < 0 || curPiece.posY > LAST_SQUARE_INDEX) {
            return false;
        }
        if(place == ' '){
            return mPBoard[curPiece.posX][curPiece.posY] == null;
        }
        else{
            return mPBoard[curPiece.posX][curPiece.posY].name == place;
        }

    }

    private void moveKing(){
       for(int i = -1; i < 2; i++){
           for(int j = -1; j < 2 ; j++){
               final int posx = i + mPiece.posX;
               final int posy = j + mPiece.posY;
               addMove(posx, posy);
           }
       }
    }

    private void moveRook(){
        for(int i = mPiece.posX - 1; i >= 0 ; i--){
            if(!addMove(i, mPiece.posY)) {
                break;
            }
        }
        for(int i = mPiece.posX + 1; i < CHESS_LENGTH ; i++){
            if(!addMove(i, mPiece.posY)) {
                break;
            }
        }
        for(int i = mPiece.posY - 1; i >= 0 ; i--){
            if(!addMove(mPiece.posX, i)) {
                break;
            }
        }
        for(int i = mPiece.posY + 1; i < CHESS_LENGTH ; i++){
            if(!addMove(mPiece.posX, i)) {
                break;
            }
        }
    }

    private void moveBishop(){
        for(int i = 1 ; i < CHESS_LENGTH ; i++){
            final int posx = mPiece.posX - i;
            final int posy = mPiece.posY - i;
            if(!addMove(posx, posy)) {
                break;
            }
        }
        for(int i = 1 ; i < CHESS_LENGTH ; i++){
            final int posx = mPiece.posX - i;
            final int posy = mPiece.posY + i;
            if(!addMove(posx, posy)) {
                break;
            }
        }
        for(int i = 1 ; i < CHESS_LENGTH ; i++){
            final int posx = mPiece.posX + i;
            final int posy = mPiece.posY + i;
            if(!addMove(posx, posy)) {
                break;
            }
        }
        for(int i = 1 ; i < CHESS_LENGTH ; i++){
            final int posx = mPiece.posX + i;
            final int posy = mPiece.posY - i;
            if(!addMove(posx, posy)) {
                break;
            }
        }
    }
    private void moveQueen(){
        moveRook();
        moveBishop();
    }
    private void moveKnight(){
        addMove(mPiece.posX - 1, mPiece.posY - 2);
        addMove(mPiece.posX + 1, mPiece.posY - 2);
        addMove(mPiece.posX + 2, mPiece.posY - 1);
        addMove(mPiece.posX - 2, mPiece.posY - 1);
        addMove(mPiece.posX - 2, mPiece.posY + 1);
        addMove(mPiece.posX + 2, mPiece.posY + 1);
        addMove(mPiece.posX + 1, mPiece.posY + 2);
        addMove(mPiece.posX - 1, mPiece.posY + 2);
    }

    private void movePawn(){
        boolean bpos2 ;
        if(mPiece.bWhite){
            addMovePawnD(mPiece.posX - 1, mPiece.posY + 1);
            addMovePawnD(mPiece.posX + 1, mPiece.posY + 1);
            bpos2 = addMovePawnF(mPiece.posX, mPiece.posY + 1);
            if(mPiece.posY != 1) {
                bpos2 = false;
            }
        }
        else{
            addMovePawnD(mPiece.posX - 1, mPiece.posY - 1);
            addMovePawnD(mPiece.posX + 1, mPiece.posY - 1);
            bpos2 = addMovePawnF(mPiece.posX, mPiece.posY - 1);
            if(mPiece.posY != SECOND_LAST_INDEX) {
                bpos2 = false;
            }
        }
        if(bpos2){
            if(mPiece.bWhite){
                addMovePawnF(mPiece.posX, mPiece.posY + 2);
            }
            else{
                addMovePawnF(mPiece.posX, mPiece.posY - 2);
            }
        }

    }
    private boolean addMove(final int posx, final int posy){
        if(posx < 0 || posx > LAST_SQUARE_INDEX) {
            return false;
        }
        if(posy < 0 || posy > LAST_SQUARE_INDEX) {
            return false;
        }
        if(posx == mPiece.posX && posy == mPiece.posY) {
            return false;
        }
        final boolean bnull = mPBoard[posx][posy] == null;
        boolean bsame = false;
        if(!bnull){
            bsame = mPBoard[posx][posy].bWhite == mPiece.bWhite;
        }
        if(bsame) {
            return false;
        }
        String strMove = "";
        strMove += String.valueOf((char)(posx + 'a'));
        strMove += String.valueOf((char)(posy + '1'));
        mLegalMoves.add(strMove);

        return bnull;
    }

    private void addMovePawnD(final int posx, final int posy){
        if(posx < 0 || posx > LAST_SQUARE_INDEX) {
            return ;
        }
        if(posy < 0 || posy > LAST_SQUARE_INDEX) {
            return ;
        }
        if(mPBoard[posx][posy] == null) {
            return;
        }
        if(mPBoard[posx][posy].bWhite == mPiece.bWhite) {
            return;
        }
        String strMove = "";
        strMove += String.valueOf((char)(posx + 'a'));
        strMove += String.valueOf((char)(posy + '1'));
        mLegalMoves.add(strMove);
    }

    private boolean addMovePawnF(final int posx, final int posy) {
        if(posx < 0 || posx > LAST_SQUARE_INDEX) {
            return false;
        }
        if(posy < 0 || posy > LAST_SQUARE_INDEX) {
            return false;
        }
        if(mPBoard[posx][posy] != null) {
            return false;
        }
        String strMove = "";
        strMove += String.valueOf((char)(posx + 'a'));
        strMove += String.valueOf((char)(posy + '1'));
        mLegalMoves.add(strMove);
        return true;
    }

    public static void main(final String[] args) {
        final ChessMove chess = new ChessMove();
        chess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Pf2");
        out.println(chess.movePiece());
    }

    private class Piece {
        private char name;
        private int posX;
        private int posY;

        private boolean bWhite;

        Piece(final String piece, final boolean bWhite2){
            
            if(piece.length() != ROW_LIST_CHECK) {
                return;
            }
            name = piece.charAt(0);
            posX = piece.charAt(1) - 'a';
            posY = piece.charAt(2) - '1';
            this.bWhite = bWhite2;

        }

        public String getPiece(){
            String str = String.valueOf(name);
            str += String.valueOf((char)(posX + 'a'));
            str += String.valueOf((char)(posY + '1'));
            return str;
        }
    }
}