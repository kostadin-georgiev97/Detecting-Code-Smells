Details of Code Smells in CS409 Test System
Your solution should process this entire system.

Bloaters
- Grid is Large class and contains long methods, and long parameter lists! It also contains examples of data classes
- Long Method - BarnsleyFern (createFern)
- Long Method - floodFill
- Primitive Obsession / Data clumps - plot, drawLine and paintComponent in BresenhamPanel
- Long Parameter List - ManOrBoy - A
- Large Class - see Grid in Switch

Abusers
- Temporary field - BarnsleyFernTwo
- Refused bequest - two cases in ChqAcc and SavingsAcc

Dispensibles
- Data Classes - Point and Triple in Cipolla (and also Message Chains)
- Dead Code in Luhn (2 cases)
- Duplicate and Dead Code in Test
- Data Classes in several other instances of other examples (Grid, Item and Phone in the FeatureEnvy package, and Parent in the MessageChains package)
Couplers
- Feature Envy - FeatureEnvy Customer and Phone, and Item and Basket
  (and Item and Phone are also Data Classes)
- Inappropriate Intimacy - Huffman code accesses the files of HuffmanLeaf, HuffmanNode and HuffmanTree (not a particularly strong example)
- Middle Man - AccountManager
- Message Chains - Client (and Parent could be flagged up as a Data Class)

FalsePositives
 - should be nothing wrong here






