package ttt2D;

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
    		v= heuristic(player,state);
		}
    	else {
    		if ( player == Constants.CELL_X)
    		{
    			v=Double.NEGATIVE_INFINITY;//??
    			for (int candidateState = 0; candidateState < nextStates.size(); candidateState++)
    			{
    				v = Math.max(v, alphabetaPrun(nextStates.get(candidateState), depth+1, alpha, beta, Constants.CELL_O));
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
    				v = Math.min(v, alphabetaPrun(nextStates.get(candidateState), depth+1, alpha, beta, Constants.CELL_X));
    				beta= Math.min (beta, v);
    				if (beta<=alpha)
    					break;
    			}    			
    			
    		}
    	}
    	return v;
    }
    
    public int heuristic(int player, GameState state)
    {
    	return -1;
    }
    
}
