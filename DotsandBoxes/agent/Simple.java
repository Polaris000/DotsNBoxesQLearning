package agent;

import java.util.Collections;
import java.util.Set;

public class Simple implements Agent
{

    public int choose_action(long state, Set<Integer> actions)
    {
        return Collections.min(actions);
    }

    public void reward(int feedback, long newState, Set<Integer> actions)
    {

    }

    public void save()
    {

    }
}
