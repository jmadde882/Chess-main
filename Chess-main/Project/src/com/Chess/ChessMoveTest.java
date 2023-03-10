package com.Chess;

import org.junit.jupiter.api.*;

import java.util.Arrays;

class ChessMoveTest {


    private ChessMove testChess;

    ChessMoveTest(){
        testChess = new ChessMove();
    }
    @BeforeEach
    void setUp() {
        testChess = new ChessMove();

    }

    @AfterEach
    void tearDown() {
        testChess = null;
    }
 private String inputValidator = "Input should be correct";
    @Test
    void readInputExceptionEmptyLine(){
        final ArrayIndexOutOfBoundsException emptyLine = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testChess.readInput("");
        });
        Assertions.assertEquals("Input should have three lines.", emptyLine.getMessage(),inputValidator );
    }
    @Test
    void readInputExceptionLessLine(){
        final ArrayIndexOutOfBoundsException lessLine = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testChess.readInput("WHITE Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5");
        });
        Assertions.assertEquals("Input should have three lines.", lessLine.getMessage(), inputValidator);
    }
    @Test
    void readInputExceptionGreaterLine(){
        final ArrayIndexOutOfBoundsException greaterLine = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testChess.readInput("WHITE Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1\nPIECE TO MOVE: Pc7");
        });
        Assertions.assertEquals("Input should have three lines.", greaterLine.getMessage(), inputValidator);
    }
    @Test
    void readInputExceptionLineFormat(){
        final IllegalArgumentException missQuote = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.readInput("WHITE Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        final IllegalArgumentException outQuote = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf1, Kg1, Pf2:, Ph2, Pg3\nBLAK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        for (final IllegalArgumentException e : Arrays.asList(missQuote, outQuote)) {
            Assertions.assertEquals("Input line format is invalid.", e.getMessage() , inputValidator);
        }
    }
    @Test
    void readInputExceptionLineStartWith() {
        final IllegalArgumentException wrongWhite = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.readInput("white: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        final IllegalArgumentException wrongBlack = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLAK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        for (final IllegalArgumentException e : Arrays.asList(wrongWhite, wrongBlack)) {
            Assertions.assertEquals("Input lines should start with 'BLACK' or 'WHITE'.", e.getMessage() ,inputValidator);
        }
    }

    @Test
    void readInputExceptionLinePiece() {
        final IllegalArgumentException emptyPiece = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.readInput("WHITE: Rf1, Kg1, , Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        final IllegalArgumentException smallPiece = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        final IllegalArgumentException outPiece = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf12, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        final IllegalArgumentException wrongCase1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rx1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });
        final IllegalArgumentException wrongCase2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rg9, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");

        });

        for (final IllegalArgumentException e : Arrays.asList(emptyPiece, smallPiece, outPiece, wrongCase1, wrongCase2)) {
            Assertions.assertEquals("Piece format of input line is invalid.", e.getMessage() , "Piece format is lik this 'Rf1'");
        }
    }

    @Test
    void readInputExceptionPiecelineFormat(){
        final IllegalArgumentException missQuote = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE Rf1");
        });
        final IllegalArgumentException outQuote = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1: R");

        });
        for (final IllegalArgumentException e : Arrays.asList(missQuote, outQuote)) {
            Assertions.assertEquals("Line format of moving piece is invalid.", e.getMessage() , "Piece line should include only one quote");
        }
    }

    @Test
    void readInputExceptionPiecelineStartWith() {
        final IllegalArgumentException wrongName = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIEC TO MOVE: Rf1");
        });
        final IllegalArgumentException wrongCase = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\npiece TO MOVE: Rf1");

        });
        for (final IllegalArgumentException e : Arrays.asList(wrongName, wrongCase)) {
            Assertions.assertEquals("Line format of moving should start with 'PIECE TO MOVE'.", e.getMessage() , "Piece line format should be correct");
        }
    }

    @Test
    void readInputExceptionPieceName() {
        final IllegalArgumentException wrongName = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Zf1");

        });
        final IllegalArgumentException wrongPos = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testChess.clearBoard();
            testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Kf1");

        });
        for (final IllegalArgumentException e : Arrays.asList(wrongName, wrongPos)) {
            Assertions.assertEquals("The name of moveing piece does not match with board.", e.getMessage() , "Piece name should be same with board position piece");
        }
    }

    @Test
    void movePieceKing() {
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Kg1");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Kg1: g2, h1", "Test king movement");
        testChess.clearBoard();
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Kb8");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Kb8: a8, c8", "Test king movement");
    }
    @Test
    void movePieceRook() {
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Rf1");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Rf1: e1, d1, c1, b1, a1", "Test Rook movement");
        testChess.clearBoard();
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5\nBLACK: Kb8, Ne8, Pa7, Pb7, Pc7, Ra5\nPIECE TO MOVE: Ra5");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Ra5: b5, c5, d5, e5, f5, a4, a3, a2, a1, a6", "Test Rook movement");
    }
    @Test
    void movePieceQueen() {
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Qd6");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Qd6: c6, b6, a6, e6, f6, g6, h6, d5, d4, d3, d2, d1, d7, d8, c5, b4, a3, c7, e7, f8, e5, f4", "Test Queen movement");
        testChess.clearBoard();
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Qe3");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Qe3: d3, c3, b3, a3, f3, g3, e2, e1, e4, e5, e6, e7, d2, c1, d4, c5, b6, f4, g5, h6, f2", "Test Queen movement");
    }

    @Test
    void movePieceBishop() {
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Bf5");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Bf5: e4, d3, c2, b1, e6, d7, c8, g6, h7, g4, h3", "Test Bishop movement");
    }
    @Test
    void movePieceKnight() {
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Ng7");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Ng7: f5, h5, e6", "Test Knight movement");
    }
private String pawnMovementMessage = "Test Pawn movement";
    @Test
    void movePiecePawn() {
        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Be8\nPIECE TO MOVE: Pf2");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Pf2: f3, f4", pawnMovementMessage);
        testChess.clearBoard();

        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Pg3");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Pg3: g4", pawnMovementMessage);
        testChess.clearBoard();

        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Pa7");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Pa7: a6", pawnMovementMessage);
        testChess.clearBoard();

        testChess.readInput("WHITE: Rf1, Kg1, Pf2, Ph2, Pg3, Bf5, Qd6\nBLACK: Kb8, Ng7, Pa7, Pb7, Pc7, Ra5, Qe3, Be8\nPIECE TO MOVE: Pc7");
        Assertions.assertEquals(testChess.movePiece(), "LEGAL MOVES FOR Pc7: d6, c6, c5", pawnMovementMessage);
        testChess.clearBoard();
    }
}