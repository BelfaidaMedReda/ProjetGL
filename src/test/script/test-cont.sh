#!/bin/sh

#Script qui execute test_context sur tous les tests Valid
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

for i in ./src/test/deca/context/valid/provided/*.deca
do
    if test_context $i 2>&1 \
        | head -n 1 | grep -q $i
    then
        echo "Echec inattendu de test_context sur " "$i"
        exit 1
    else
        echo "OK" "$i"

fi
done

for i in ./src/test/deca/context/invalid/provided/*.deca
do
    if test_context $i 2>&1 \
        | grep -q -e $i
    then
        echo "Echec attendu de test_context pour " "$i"
    else
        echo "Erreur non detectee par test_context pour " "$i"
        exit 1
    fi
done