public class test {
	
    public static void main()
    {
    	heuristic();
    }
    
    
    public double heuristic()   
    {
    	GameState.BOARD_SIZE=4;
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
    	
    	//bottom left to top right diagonal
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

    		
    	
    	
    	//top left to bottom right diagonal
		int x_4 = 0;
		int o_4 = 0;
		
    	for (int i=0; i<GameState.BOARD_SIZE; i++)        //in this case GameState.BOARD_SIZE = 4
    	{    		
    		for (int j=GameState.BOARD_SIZE-1; j>=0; j--)
    		{
    			if (state.at(i,j) == Constants.CELL_X) {
    				x_4 += 1;
    			}
    			else if (state.at(i,j) == Constants.CELL_O) {
    				o_4 += 1;
    			}
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