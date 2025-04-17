An Effect is made of \
"checks" : [] of [] made out of 3 parts (1 "attribute") (operand) (1 value) \
*attribute* can be :[\
attackUs.pp,\
AttackUs.isPhysical,\
attackUs.accuracy,\
attackUs.critRate,\
attackUs.baseDamage, # damage of move in json\
attackUs.damage, # damage after calculation\
attackUs.type, # Check type enum (strings values)\
attackUs.priority,\
attackEnemy.pp, \
... (same of AttackUs),\
us.stats.hp.current,     # taken from actualStats\
us.stats.hp.max,\
us.stats.attack,\
us.stats.defense,\
us.stats.special-attack,\
us.stats.special-defense,\
us.stats.speed,\
us.tempStatsBonus.hp ...,\
us.nature # Check Nature enum (strings values)\
us.IV.hp ...,\
us.EV.hp ...,\
us.weight,\
us.type1,\
us.type2 # might be null\
us.gender,\
us.statusCondition # Check StatusCondition enum (string values),\
enemy.hp ... (same of us),\
weather #Check weather enum (strings values)\
]\
*operand* can be : ["==","<",">","<=",">=","!="]\
\
*value* can be : A matematical expression\
(it will be interpreted like a math one, if you want to be sure put parentesis)\
*NB*:If it is a String put '' around it\
\
The Check is considered passed is ALL the checks are TRUE\
and a activation : [] of [] made out of 2 parts (1 "attribute") (1 value)\
\
EX:\
Check abilities/overgrow.json effect\
\
