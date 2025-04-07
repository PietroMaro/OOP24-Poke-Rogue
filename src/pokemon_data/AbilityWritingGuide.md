An Ability is composed of\
"situationChecks" : ["attack","attacked","switchIn","switchOut","neutral"]\
neutral is a moment before attack phase begin\
"effect" Check effect guide \
Ex:\
{\
    "situationChecks" : "attack",\
    "effect" : {\
        "checks" : [["attack.type", "==" , "grass"],\
                    ["us.hp","<=","us.hp \ 3"]],\
        "activation" : [["attack.baseDamage","attack.baseDamage + (attack.baseDamage \ 2)"]]\
    }\
}\
