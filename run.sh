./gradlew build

rcmd="java -classpath 'build/classes/java/main/:libs/*' dev.gavinthomas.tictactoe.Main"

#konsole --workdir "/home/gavin/IdeaProjects/TicTacToe/" -e "$rcmd"
konsole --workdir "/home/gavin/IdeaProjects/TicTacToe/" --noclose -e "$rcmd"
#java -classpath "out/production/TicTacToe/:libs/*" Main