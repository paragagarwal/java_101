JFLAGS = -g
JC = javac
RM= rm
default: 
	$(JC) $(JFLAGS) MainClass.java
	ln -s $(shell pwd)/run.sh /usr/local/bin/equation_solver
	PATH=/usr/local/bin/equation_solver:${PATH}
	export PATH=$(PATH)
exec-test:
	./run_test.sh
clean: 
	rm /usr/local/bin/equation_solver
	$(RM) *.class
