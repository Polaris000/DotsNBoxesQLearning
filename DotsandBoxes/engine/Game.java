package engine;

import java.util.*;

import agent.Agent;

public class Game
{
   
    List<Agent> agents;

    ScoreTable scoring;

    Random random;

    Grid grid;

    int rounds;

    int roundsBetweenResults;

    private static int turn_end_reward(int num_boxes)
    {
        return 0;
    }

    private static int round_end_reward(boolean win)
    {
        if (win) return 10;
        return -10;
    }

    public Game(Grid grid, Agent agent1, Agent agent2, int rounds)
    {
        this(grid, agent1, agent2, rounds, -1);
    }

    public Game(Grid grid, Agent agent1, Agent agent2, int rounds, int roundsBetweenResults)
    {
        agents = new Vector<Agent>(2);
        scoring = new ScoreTable();

        agents.add(agent1);
        agents.add(agent2);
        scoring.add_agent_entry(agent1);
        scoring.add_agent_entry(agent2);

        random = new Random(System.currentTimeMillis());

        this.grid = grid;
        this.rounds = rounds;
        this.roundsBetweenResults = roundsBetweenResults;

    }
    
    public void next_round()
    {
        int startIndex = random.nextInt(agents.size());
        int index = startIndex;
        Set<Integer> available = null;

        int[] previousNumBoxes = new int[agents.size()];

        for (int i = 0; i < previousNumBoxes.length; ++i)
            previousNumBoxes[i] = -1;

        grid.reset();
        scoring.reset_boxes();

        while (!grid.is_finished())
        {
            Agent current = agents.get(index);

            int num_boxes;

            int action;
            long state = grid.get_id();
            available = grid.get_available();

            if (previousNumBoxes[index] != -1)
            {
                current.reward(turn_end_reward(previousNumBoxes[index]), state, available);
            }

            action = current.choose_action(state, available);

            num_boxes = grid.play_line(action);

            previousNumBoxes[index] = num_boxes;

            if (num_boxes > 0)
            {
                scoring.increment_complete_boxes(current, num_boxes);
            }
            
            else
            {
                index = (index + 1) % agents.size();
            }

            for (Agent a: agents)
                a.save();
        }

        List<Agent> winning_agent = scoring.winner();

        if (winning_agent.size() == 1)
        {
        for (int i = 0; i < agents.size(); ++i)
        {
            // System.out.println("Size:" +  winning_agent.size());
            Agent agent = agents.get(i);
            
            if (winning_agent.contains(agent))
            {
                agent.reward(round_end_reward(true),
                        grid.get_id(), null);
                scoring.inc_score(agent);

                // System.out.println(String.format(
                //         "Agent %s won the round. Boxes: %d", agent
                //                 .getClass().getSimpleName(), scoring
                //                     .completed_boxes(agent)));
            }
                
            else
            {
                agent.reward(round_end_reward(false),
                        grid.get_id(), null);
            }
        }
    }

        else
        {
            // Give positive feedback to the agent that started last.
            for (int i = 0; i < agents.size(); ++i)
            {
                Agent agent = agents.get(i);

                if ((startIndex - 1 + agents.size()) % agents.size() == i)
                {
                    scoring.inc_score(agent);
                    agent.reward(round_end_reward(true),
                            grid.get_id(), null);
                }
                
                else
                {
                    agent.reward(round_end_reward(false),
                            grid.get_id(), null);
                }
            }
        }
    }
    

    public void run()
    {
        int i = 0;
        for (; i < rounds; ++i)
        {
            if (i > 0 && roundsBetweenResults > 0 && i % roundsBetweenResults == 0)
            {
                for (String result : get_results(i))
                {
                    System.out.println(result);
                }
            }

            // System.out.println("Playing new round.");
            next_round();
        }

        for (String result : get_results(i))
        {
            System.out.println(result);
        }
    }


    private List<String> get_results(int rounds)
    {
        List<String> results = new LinkedList<String>();

        for (int i = 0; i < agents.size(); ++i)
        {
            Agent agent = agents.get(i);
            int wins = scoring.get_score(agent);
            
            results.add(String.format("#%d %s: %d wins, %d losses. Share: %f", (i + 1),
                        agent.getClass().getSimpleName(), wins, rounds - wins, (double)wins / rounds));

        }

        return results;
    }

}
