# How it works
The program goes through every word in the words list and ranks each letter based on frequency.

Words with more common letters are prioritized as guesses (letter frequency metric).

Each guess, it eliminates words that it knows cannot the target from the Words file.

Then, it selects its best guess again based on the letter frequencies metric.

Averages around 4.47 guesses per word.

### Comparison to other algorithms
Most algorithms generally average 3.5 guesses per word and do so by attempting to eliminate as many words as possible per guess.

This bot instead tries to pick the most likely word (imitating what a player would do on their last guess) each step.

A good theoretical would attempt to eliminate as many words as possible for the first 5 guesses, but use my bot to make the 6th and final guess.

# How to use
### Playing the game
If you run WordleGame, this will allow you to play the game with the target word as "PARTY".

You can change the word by running wg.playGame("[your word here]") with your 5-letter word here in the brackets.

After each guess, a 5-letter sequence consisting of Bs, Gs, and Ys will be displayed.

B means that letter is not in the word. Y means that letter is in the word, but in the wrong position. G means right letter, right position.

You are limited to 6 guesses.

### Using the bot
If you run Guesser, this will immediately output what the bot thinks the best first word is given the current Words file.

You can edit the words in the Words file to include only the ones you want the guesser to consider.

Then it waits for a 5 letter input consisting of Bs, Gs, and Ys corresponding the the output Wordle gives when you enter that guess.

Then it will show you its next best guess.

This process repeats until the word is guessed.

### Additional functionalities
You can generate a list of good first words by running FirstWord (a sample generation is included in GoodFirstWords).

To see personally how this algorithm performs on every word in the included words list, run TestGuesser.
