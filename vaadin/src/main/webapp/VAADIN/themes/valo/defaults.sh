#!/bin/bash

for component in absolutelayout accordion button calendar checkbox colorpicker combobox csslayout customcomponent customlayout datefield dragwrapper form formlayout grid gridlayout label link loginform menubar nativebutton nativeselect optiongroup orderedlayout panel popupview progressbar slider splitpanel table tabsheet textfield textarea richtextarea tree treetable twincolselect upload window
do 
	rm -f tmp tmp2 tmp3 tmp4
	cp components/_$component.scss tmp2
	maxlen=0
	maxlen2=0
	for var in `grep '^\$v-' shared/_variables.scss |cut -d: -f 1` 
	do
		cvar=`echo $var|sed "s/v-/v-$component-/"`
		len="${#cvar}"
		len2="${#var}"
		maxlen=$(($len>$maxlen?$len:$maxlen))
		maxlen2=$(($len2>$maxlen2?$len2:$maxlen2))
	done
	
	for var in `grep '^\$v-' shared/_variables.scss |cut -d: -f 1` 
	do 
		cvar=`echo $var|sed "s/v-/v-$component-/"`
		printf "%-"$maxlen"s%s" $cvar " : " >> tmp
		printf "%-"$maxlen2"s%s\n" $var " !default;" >> tmp
		cat tmp2|sed "s/$var/$cvar/g" > tmp4 ; mv tmp4 tmp2
	done

	( cat tmp ; cat tmp2) > new/_$component.scss
done
