make:
	mvn package

editor: make
	java -cp target/group-text-1.0-SNAPSHOT.jar com.mycompany.app.App --editor

server: make
	java -cp target/group-text-1.0-SNAPSHOT.jar com.mycompany.app.App --server

count-lines:
	@echo "Counting lines of code in all Git-tracked files..."
	git ls-files | xargs wc -l
