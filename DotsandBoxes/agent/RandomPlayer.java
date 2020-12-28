package agent;

import java.util.*;

public class RandomPlayer implements Agent{
    
    private Random random;

    public RandomPlayer()
    {
        this(System.currentTimeMillis());
    }

    public RandomPlayer(long seed)
    {
        random = new Random(seed);
    }

    public int choose_action(long state, Set<Integer> actions)
    {
        return (Integer) actions.toArray()[random.nextInt(actions.size())];
    }

    public void reward(int feedback, long newState, Set<Integer> actions)
    {

    }

    public void save()
    {

    }
}
