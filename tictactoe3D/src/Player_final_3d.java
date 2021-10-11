import java.util.*;
import java.lang.Math;


public class Player_final {
    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
	//create a 2D array containing all the possible winning combinations (all the ways of getting 4 in a row). Uses indexing found in GameState
	int possible_combos [][] =
	{
			
	//all of the rows in each layer
	//layer 0
	{0, 1, 2, 3},
	{4, 5, 6, 7},
	{8, 9, 10, 11},
	{12, 13, 14, 15},
	
	//layer 1
	{16, 17, 18, 19},
    {20, 21, 22, 23},
    {24, 25, 26, 27},
    {28, 29, 30, 31},
    
    //layer 2
    {32, 33, 34, 35},
    {36, 37, 38, 39},
    {40, 41, 42, 43},
    {44, 45, 46, 47},
    
    //layer 3
    {48, 49, 50, 51},
    {52, 53, 54, 55},
    {56, 57, 58, 59},
    {60, 61, 62, 63},
    
    //all of the columns in each layer
    //layer 0
    {0, 4, 8, 12},
    {1, 5, 9, 13},
    {2, 6, 10, 14},
    {3, 7, 11, 15},

    //layer 1
    {16, 20, 24, 28},
    {17, 21, 25, 29},
    {18, 22, 26, 30},
    {19, 23, 27, 31},

    //layer 2
    {32, 36, 40, 44},
    {33, 37, 41, 45},
    {34, 38, 42, 46},
    {35, 39, 43, 47},

    //layer 3
    {48, 52, 56, 60},
    {49, 53, 57, 61},
    {50, 54, 58, 62},
    {51, 55, 59, 63},
    
    //all the diagonal combinations in each layer
    //layer 0
    {0, 5, 10, 15},
    {3, 6, 9, 12},
    
    //layer 1
    {16, 21, 26, 31},
    {19, 22, 25, 28},
    
    //layer 2
    {32, 37, 42, 47},
    {35, 38, 41, 44},
    
    //layer 3
    {48, 53, 58, 63},
    {51, 54, 57, 60},
    
    
    //all of the vertical column combinations- one value from each layer
    {0, 16, 32, 48},
    {1, 17, 33, 49},
    {2, 18, 34, 50},
    {3, 19, 35, 51},

    {4, 20, 36, 52},
    {5, 21, 37, 53},
    {6, 22, 38, 54},
    {7, 23, 39, 55},

    {8, 24, 40, 56},
    {9, 25, 41, 57},
    {10, 26, 42, 58},
    {11, 27, 43, 59},

    {12, 28, 44, 60},
    {13, 29, 45, 61},
    {14, 30, 46, 62},
    {15, 31, 47, 63},
    
    //3D diagonals direction 1- one value from each layer
    {0, 17, 34, 51},
    {3, 18, 33, 48},
    
    {4, 21, 38, 55},
    {7, 22, 37, 52},
    
    {8, 25, 42, 59},
    {11, 26, 41, 56},
    
    {12, 29, 46, 63},
    {15, 30, 45, 60},
    
    //3D diagonals direction 2- one value from each player
    {0, 20, 40, 60},
    {12, 24, 36, 48},
    
    {1, 21, 41, 61},
    {13, 25, 37, 49},
    
    {2, 22, 42, 62},
    {14, 26, 38, 50},
    
    {3, 23, 43, 63},
    {15, 27, 39, 51},  
 
    //3D cross diagonals- one value from each layer
    {0, 21, 42, 63},
    {3, 22, 41, 60},
    {12,25,38,51},
    {15,26,37,48},

	};
	
    public GameState play(final GameState gameState, final Deadline deadline)
    {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }
        
        int depth=2;
        int best_i=0;
        double max_v= Double.NEGATIVE_INFINITY;
        for (int i=0; i< nextStates.size(); i++)
        {
        	double v= alphabetaPrun (nextStates.elementAt(i), depth, max_v, Double.POSITIVE_INFINITY, 2);
        	if (v>max_v)
        	{
        		max_v=v;
        		best_i=i;
        	}
        }
       
        return nextStates.elementAt(best_i);
    } 
    
    public double alphabetaPrun (GameState state, int depth, double alpha, double beta, int player)
    {
    	
        if (state.isEOG()){                  

            if (state.isXWin()){                           
                return Double.POSITIVE_INFINITY;
            }
            else if (state.isOWin()){
                return Double.NEGATIVE_INFINITY;
            }
            else {
            	return 0;
            }
            
        }
        
        if(depth == 0){
        	return heuristic(state);
        }
        
           
    	double v=0; 
    	Vector<GameState> nextStates = new Vector<GameState>();
    	state.findPossibleMoves(nextStates);
    	
    	
    		if ( player == Constants.CELL_X)
    		{
    			v=Double.NEGATIVE_INFINITY;
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
    	
        
    	return v;
    }
    
    
    public double heuristic(GameState state) {
    	
    	double finalScore = 0;
    	int scoreList [] = {0,1,10,100,100000};
    	
    	for (int i=0; i<76; i++)        //there are 76 winning combinations
    	{
    		int x_count1 = 0;
    		int o_count1 = 0;
        	
    		for (int j=0; j<GameState.BOARD_SIZE; j++)
    		{
    			if (state.at(possible_combos[i][j]) == Constants.CELL_X) {
    				x_count1 += 1;
    			}
    			else if (state.at(possible_combos[i][j]) == Constants.CELL_O) {
    				o_count1 += 1;
    			}
    		}

    		if ( (x_count1 != 0) && o_count1 == 0) {
    			finalScore += scoreList[x_count1];
    		}
    		
        	else if ( (o_count1 != 0) && x_count1 == 0) 
        	{
        		finalScore -= scoreList[o_count1];
    	}
    	}
    	
    	return finalScore;
    
    }
    
}
