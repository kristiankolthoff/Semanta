# Semanta -Topic-Based Crossword Generator

In this project we develop a Semantic Web application that automatically generates crossword 
puzzles based on user-specified topics in a generic way. Hence, the final application can be 
recognized as a learning tool focusing on topics the user is interested in. However, the application 
is not restricted to high-level topics but rather allows to incorporate most of the entities which 
can be found in corresponding RDF knowledge sources as a topic for the crossword puzzle. 

Most of the traditional crossword generator tools have a fixed crossword structure and employ backtracking 
algorithms by exploiting manually crafted databases of clue-answer pairs. Hence, the creation of the 
crossword puzzle is only semi-automatic, still requiring human knowledge and expertise. Therefore, 
different topics can only be represented in the crossword by applying different topic clue-answer 
databases. 

The approach of creating crosswords implemented in Semanta, however, is reversed. Instead 
of starting with a fixed structure and filling in words, Semanta firstly extracts the topic-related 
words and then dynamically creates the crossword structure. Hence, the user specifies an interesting
topic, receives a list of the most probably resources that belong to the given string and selects the 
corresponding resource. In addition, the user can directly generate a crossword puzzle without selecting
one of the search results. These options allow the user to select not only more specific topics but 
also provides a complete automated pipeline of the crossword generation process. Based on the selected 
resource and a specified difficulty level, the crossword puzzle generation algorithm queries a RDF knowledge 
base to obtain topic-related resources and finally establishes a new crossword puzzle based on the retrieved
resources. Note that the main RDF data processing tasks are to find topic-related resources and to generate
meaningful clues for the sought-after words. In addition, we integrate the option to retrieve four possible
answers (distractors) to facilitate the solving of the crossword puzzle. Here, the application is required 
to automatically generate reasonable distractors adaptive to a difficulty level given the actual answer resource.
