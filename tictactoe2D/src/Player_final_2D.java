import java.util.*;
import java.lang.Math;


public class Player {
    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState gameState, final Deadline deadline)
    {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }
        
        int currentPlayer=((gameState.getNextPlayer() == Constants.CELL_O) ? 1 : 2);
        int depth=4;
        int best_i=0;
        double max_v= Double.NEGATIVE_INFINITY;
        for (int i=0; i< nextStates.size(); i++)
        {
        	double v= alphabetaPrun (nextStates.elementAt(i), depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, currentPlayer);
        	if (v>max_v)
        	{
        		max_v=v;
        		best_i=i;
        	}
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
//        Random random = new Random();
//        return nextStates.elementAt(random.nextInt(nextStates.size()));
        

        
        return nextStates.elementAt(best_i);
    } 
    
    public double alphabetaPrun (GameState state, int depth, double alpha, double beta, int player)
    {
    	double v=0; 
    	Vector<GameState> nextStates = new Vector<GameState>();
//    	nextStates.size();
    	state.findPossibleMoves(nextStates);
    	
    	
    	if (depth==0 || nextStates.size() == 0)
		{
    		v= heuristic(state);
		}
    	else {
    		if ( player == Constants.CELL_X)
    		{
    			v=Double.NEGATIVE_INFINITY;//??
    			for (int candidateState = 0; candidateState < nextStates.size(); candidateState++)
    			{
    				v = Math.max(v, alphabetaPrun(nextStates.get(candidateState), depth-1, alpha, beta, Constants.CELL_O));
    				alpha= Math.max (alpha, v);
    				if (beta<=alpha)
    					break;
    			}
    		}
    		else // player == Constants.CELL_O
    		{
    			v=Double.POSITIVE_INFINITY;
    			for (int candidateState = 0; candidateState < nextStates.size(); candidateState++)
    			{
    				v = Math.min(v, alphabetaPrun(nextStates.get(candidateState), depth-1, alpha, beta, Constants.CELL_X));
    				beta= Math.min (beta, v);
    				if (beta<=alpha)
    					break;
    			}    			
    			
    		}
    	}
    	return v;
    }
    
//    public int heuristic(int player, GameState state)
//    {
//    	return -1;
//    }
    
    
    //evaluation function
    //calculates the scores from the rows, columns and diagonals separately
    //returning a positive score means player 1 (X) won, negative means player 2 (O) won, 0 means draw
    //if calculating score for a row for X, will only add up score if there are only X's in the row
    public double heuristic(GameState state)   
    {
    	double finalScore = 0;
    	int scoreList [] = {0,1,10,100,100000};
    	
    	//rows
    	for (int i=0; i<GameState.BOARD_SIZE; i++)        //in this case GameState.BOARD_SIZE = 4
    	{
    		int x_count1 = 0;
    		int o_count1 = 0;
        	
    		for (int j=0; j<GameState.BOARD_SIZE; j++)
    		{
    			if (state.at(i,j) == Constants.CELL_X) {
    				x_count1 += 1;
    			}
    			else if (state.at(i,j) == Constants.CELL_O) {
    				o_count1 += 1;
    			}
    		}

    		if ( (x_count1 != 0) && o_count1 == 0) {
    			finalScore += scoreList[x_count1];
    		}
    		
//    		else if ( (o_count1 != 0) && x_count1 == 0) {
//    			finalScore -= scoreList[o_count1];
//    		}    		
    	}
    	
    	//columns
    	for (int i=0; i<GameState.BOARD_SIZE; i++)        //in this case GameState.BOARD_SIZE = 4
    	{    		
    		int x_count2 = 0;
    		int o_count2 = 0;
        	
    		for (int j=0; j<GameState.BOARD_SIZE; j++)
    		{
    			if (state.at(j,i) == Constants.CELL_X) {
    				x_count2 += 1;
    			}
    			else if (state.at(j,i) == Constants.CELL_O) {
    				o_count2 += 1;
    			}
    		}
    	
    		if ( (x_count2 != 0) && o_count2 == 0) {
    			finalScore += scoreList[x_count2];
    		}
    		
//    		else if ( (o_count2 != 0) && x_count2 == 0) {
//    			finalScore -= scoreList[o_count2];
//    		}	
    	}
    	
    	//top left to bottom right diagonal
    	int x_count3 = 0;
		int o_count3 = 0;
    	for (int i=0; i<GameState.BOARD_SIZE; i++)        //in this case GameState.BOARD_SIZE = 4
    	{    		
    		
    		
    		if (state.at(i,i) == Constants.CELL_X) {
    			x_count3 += 1;
    		}
    		else if (state.at(i,i) == Constants.CELL_O) {
    			o_count3 += 1;
    		}
    		
    	}

    		if ( (x_count3 != 0) && o_count3 == 0) {
    			finalScore += scoreList[x_count3];
    		}
    	
//    		else if ( (o_count3 != 0) && x_count3 == 0) {
//    			finalScore -= scoreList[o_count3];
//    		}

    		
    	
    	
    	//bottom left to top right diagonal
		int x_4 = 0;
		int o_4 = 0;
		
    	for (int i=0; i<GameState.BOARD_SIZE; i++)        //in this case GameState.BOARD_SIZE = 4
    	{    		
    		
    			if (state.at(i,GameState.BOARD_SIZE-1-i) == Constants.CELL_X) {
    				x_4 += 1;
    			}
    			else if (state.at(i,GameState.BOARD_SIZE-1-i) == Constants.CELL_O) {
    				o_4 += 1;
    			}
    		
    	}

    	if ( (x_4 != 0) && o_4 == 0) { 
    		finalScore += scoreList[x_4];
    	}
    	
//    	else if ( (o_4 != 0) && x_4 == 0) {
//    		finalScore -= scoreList[o_4];
//    	}
    	
    	return finalScore;   		
    	
    }
    
}
