# 5CAux

## Description

5CAux is a prototype music analytics application designed for students at the Claremont Colleges (the 5Cs). The project analyzes listening data to display trends in what students are listening to and to help individuals discover others with similar musical tastes. By collecting, sorting, filtering, and matching listening behavior, 5CAux aims to make music culture within the 5Cs more visible to facilitate community connection.

The application is implemented as a command-line Java program that loads user, song, and listening history data from CSV files and allows users to explore music trends and find potential matches through an interactive menu.

## Motivation/Background

People are often interested in the music culture around them, but this information is usually private or individualized. Existing tools like Spotify Wrapped summarize listening habits for individuals, but they do not allow for meaningful comparison across a community. As a result, it can be difficult—especially for students with niche tastes or lower sociability to find others with similar musical interests.

Through interviews and informal conversations, we found that many students enjoy bonding over music and expressed genuine excitement about the idea of a tool that reveals what others are listening to as a whole. Music also serves as an easy and low-stakes conversation starter that can reduce social anxiety when meeting new people.

This project was informed by that need-finding process. 5CAux introduces a new way for students to explore shared cultural interests and potentially form connections, while remaining mindful of ethical considerations around data use and categorization.

## Features

- **Sorting**
  - View the most-played songs among 5C students:
    - All time
    - This year
    - This semester
    - Past 24 hours
  - Songs are ranked by number of plays, with tie-breaking handled alphabetically.

- **Filtering**
  - Filter listening data by:
    - School (Pomona, Scripps, CMC, Harvey Mudd, Pitzer)
    - Class year
    - Major
    - Genre
  - Filters can be combined without restarting the program.

- **Matching**
  - Find the most musically similar user based on shared listening history.
  - Similarity is computed using a comparison of unique songs listened to.
  - Users can initiate matching with a single command.

- **Data Generation**
  - The data generation creates realistic sample datasets for users, songs, and listening history.
  - This allows the application to be tested at scale without requiring real user data.

## Usage

This project is written in Java and is run from a `main` method using a command-line interface.

### Running the Program
- Ensure you have a standard Java Development Kit (JDK) installed.
- The program expects CSV data files (`Users.csv`, `Songs.csv`, `SongPlays.csv`) to be available in the appropriate data directory.
- Run the `Main` class to start the application.

### Using the Application
Once running, the program presents an interactive menu that allows users to:
- View top songs over various time ranges
- Add or remove filters (school, year, major, genre)
- Combine multiple filters at once
- Clear all active filters
- Find the most similar user based on listening history

Example actions include:
- Viewing the top 10 songs played by students at a specific school
- Narrowing results to a particular genre and class year
- Finding a musical match by entering a user ID

<!--
OPTIONAL:
If you want to include screenshots or example terminal output later, you can add a subsection here, e.g.:

### Example Output
![Screenshot description](path/to/image.png)

This is not required for a CS class project.
-->

## Authors

- Alexander Adhikari
- Julian Chumacero
- Andrew Lim

This project was completed collaboratively, with all members contributing to design, implementation, and testing.

## Acknowledgments

We would like to thank **Professor Jingyi Li** for guidance and feedback throughout the project, including design check-ins and discussions that helped shape our approach.

We also acknowledge the use of **Google Gemini** to assist in generating synthetic data for testing purposes.

