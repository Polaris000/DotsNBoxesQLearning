package agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import agent.QL.StateMatrix;

public class QLAgent implements Agent
{
    
    public static final float default_discount = 0.5f;
    public static final float default_lr = 0.8f;
    public static final float default_epsilon = 0.2f;    

    private StateMatrix stateMatrix;

    private String filename;

    private long lastState;

    private int lastAction;

    private final float discountFactor;

    private final float learningRate;

    private final float epsilon;

    boolean training;


    public QLAgent(StateMatrix matrix, String filename)
    {
        this(matrix, default_discount, default_lr, default_epsilon, filename);
    }

    public QLAgent(StateMatrix matrix,
            float discountFactor, float learningRate, float epsilon, String filename)
    {
        this.stateMatrix = matrix;
        this.discountFactor = discountFactor;
        this.learningRate = learningRate;
        this.epsilon = epsilon;
        this.filename = filename;
    }

    public int choose_action(long state, Set<Integer> actions)
    {
        lastState = state;

        float max = -Float.MAX_VALUE;
        lastAction = -1;

        float choice = (float) Math.random();
        float eps = 1 - epsilon - epsilon/actions.size();

        // explore
        if (choice > eps)
        {   //pick any action
            Random rand = new Random(42);
            int actionind = rand.nextInt(actions.size());

            List<Integer> actionlist = new ArrayList<>(actions);
            lastAction = actionlist.get(actionind);

            // System.out.println(String.format(" %f: Exploring action %d for state %d.", choice, lastAction, state));
        }

        // exploit
        else
        {   // pick best action
            for (Integer i : actions)
            {
                // System.out.println(String.format("Exploiting action %d for state %d.",
                //             i, state));

                if (stateMatrix.get_q_value(state, i) > max)
                {
                    max = stateMatrix.get_q_value(state, i);
                    lastAction = i;
                }
            }   
        }

        return lastAction;
    }

    public void reward(int feedback, long newState, Set<Integer> actions)
    {

        float q_prev = stateMatrix.get_q_value(lastState, lastAction);

        float max = 0;

        if (actions != null)
        {
            max = -Float.MAX_VALUE;
            for (Integer i : actions)
            {
                if (stateMatrix.get_q_value(newState, i) > max)
                {
                    max = stateMatrix.get_q_value(newState, i);
                }
            }
        }

        float q_new = q_prev + learningRate * (feedback + discountFactor * max - q_prev);

        // System.out.println(String.format("Saving Q-value (%d, %d, %f) %f", lastState,
        //             lastAction, q_new, max));

        stateMatrix.set_q_value(lastState, lastAction, q_new);
    }


    public void save()
    {
        // System.out.println(String.format("QLearning score: %d", numPoints));
        StateMatrix.save_table(this.filename,this.stateMatrix);
    }

}

