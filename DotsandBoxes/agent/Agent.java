package agent;

import java.util.Set;

public interface Agent
{
    public int choose_action(long state, Set<Integer> actions);

    public void reward(int feedback, long newState, Set<Integer> actions);

    public void save();
}
