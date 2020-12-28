# DotsNBoxesQLearning

## Abstract
Reinforcement learning is an area of machine learning that is concerned with algorithms for training agents to perform some task within their environments. RL algorithms have a concept of rewards for agents. The agents are given rewards depending upon their performance on their tasks and the RL algorithms teach the agents to take actions that maximize overall reward. Q-Learning is a reinforcement learning algorithm that teaches agents to learn a value for every possible action in every state. The possible configurations of the agent and the environment are the states for the algorithm. Once the agent is properly trained, the action with the maximum Q-value at each state will lead the agent to the most optimal outcome. 

Q-Learning is parameterized by three main parameters which affect how well the trained agent performs. The **learning rate** determines how much weight is assigned to new information received when performing a new iteration vs the old information from all the previous iterations. If this parameter is too high, there will be too much fluctuation in the Q-values with every iteration and the training will be unstable. If it is too low, the agent will take too long to learn proper Q-values. 

The second parameter, **discount factor**, determines how much weight is given to future rewards as compared to immediate rewards. If the discount factor is low, the agent will more strongly favour immediate rewards while a high discount factor favours high rewards in the future. 

![](/assets/qlearning.PNG)

The third parameter **epsilon** controls the degree of exploration. As the agent is being trained some actions may initially appear to lead to a high reward. If the agent always picks the action with the best Q-value, during training, it will not be able to visit all state-action pairs. The epsilon factor allows the agent to “explore” during training time by picking an action which appears to be sub-optimal in the hope that it will lead to high rewards in the future. A high epsilon means that the agent will perform too much exploration, leading to slower convergence while a low epsilon leads to low exploration which can result in actually optimal actions for each state not being found.


## The DotsNBoxes Game
we use Q-Learning to train an agent to play the game dots and boxes. This game consists of a board with a grid of dots. During each round a player connects two dots with a horizontal or vertical line. If a player draws a line that closes a box, they get a point. The player with the maximum number of points wins. For this game, every board configuration is a state for the Q-Learning algorithm and actions consist of drawing horizontal or vertical lines between available dots.

![](/assets/dnb.PNG)


## Project Implementation
Our goal was to understanding how QLearning worked in the context of the two-player DotsNBoxes game. We played different combinations of three kinds of agents in pairs and analyzed the results. These agents were:
- QLearning Agent: uses the QLearning Algorithm
- Random Agent: randomly chooses an available line
- Simple Agent: chooses the numerically smallest line available (lines are numbered starting from 0)

We analyzed results based on the three parameters: learning rate, discount factor and epsilon and played 500,000 games between any given pair of agents on a 9-dot square grid. The following summarizes the experiments we conducted.

    Q-Learning vs Random (Q1)
    Q-Learning vs Simple (Q3)
    Q-Learning vs Q-Learning (Q2)
    Q2 vs Random (Q5)
    Q1 vs Q-Learning (Q4)
    Q4 vs Q5 (Q6)
    Q3 vs Q1 (Q7)
    Q1 vs Q2 (Q8)
    
#### Note:
In the experiment representation above, `<Agent 1> <Agent 2> (<Agent 3>)` means that `Agent 1` is trained by playing against `Agent 2` and the resulting trained agent would be `Agent 3`.

    
## Results
We noticed the following trends in the experiments:
- A high epsilon is very harmful towards agent performance. Optimal performance is achieved at around 0.1 with rapid degradation on going up to 0.5. 
- The effect of learning rate and discount factor varies from case to case. When training two Q-learning agents against one another, a lower learning rate slightly improves final performance.
- When training two Q-learning agents with different levels of previous experience, both converge to equivalent final performance. Also, the order in which experience is gathered does not matter much.
- In experiment 7, we see that Q1 performs slightly better than Q3 initially and in experiment 8 we see that Q2 performs significantly better than Q1 initially. This indicates that training against a random agent is better than simple agent while training against another Q-learning agent gives the best performance.

To see the win share vs games played plots and a detailed analysis, have a look at the **Experiments** section of our [report](Report.pdf).

## Usage

Bootstrapper.java

Running this file starts the experiment between the two specified agents.   

    $ javac Bootstrapper.java
    $ java Bootstrapper <grid size: the number of dots on any side>
    
The agents can be specified by modifying the [agents section](https://github.com/Polaris000/DotsNBoxesQLearning/blob/cab5389d0819a853f48b14734a2ca80d300ef43c/DotsandBoxes/Bootstrapper.java#L70) of Bootstrapper.java.

```java
// define agents in agentParams
// agent 1
agentParams.add("Random");
filenames.add("./Q1");
lrs.add(0.6f);
discounts.add(0.5f);
epsilons.add(0.1f);

// agent 2
agentParams.add("QLAgent");
filenames.add("./Q3");
lrs.add(0.6f);
discounts.add(0.5f);
epsilons.add(0.1f);
```


    
## Acknowledgements
This project is a team effort. Contributions were made by:
- Aniruddha Karajgi
- Atmadeep Banerjee


For more information regarding this project, have a look at our [report](Report.pdf).
  
  
