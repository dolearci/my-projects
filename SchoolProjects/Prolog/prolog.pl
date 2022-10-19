delitelnost(X,Y):-
N is Y*Y,
N =< X,
X mod Y =:= 0.

delitelnost(X,Y):-
Y < X,
Y1 is Y+1,
delitelnost(X,Y1).

prvocislo(X):-
Y is 2, X > 1, \+delitelnost(X,Y).

uloha18([],0).
uloha18([H|T],DLZKA):-uloha18(T,DLZKAFIN),prvocislo(H),DLZKA is DLZKAFIN.
uloha18([H|T],DLZKA):-uloha18(T,DLZKAFIN),not(prvocislo(H)),DLZKA is DLZKAFIN + 1.
