# Stone Wallet

A small project that allows trading in Brita, Bitcoin and Real
<br>
- Brita can swap to Bitcoin;
- Bitcoin can swap to Brita;
- Real buy Brita or Bitcoin;
- Brita or Bitcoin can be sold in Real.

## Requirements

About cryptocurrency financial transactions, it is correct to state that:

* The customer can sell their cryptocurrencies or exchange one for another.

* The customer needs to know the balance broken down for each currency.

* The customer needs to have a statement of financial transactions.

## Todo List
- [X] Readme
- [X] App Concept
- [X] Basic Layout
- [X] Basic Architecture
- [X] Util Libraries
- [X] Android Permissions
- [X] Basic Tests
- [X] Room
- [ ] Enchantment Layout
- [ ] i18n
- [ ] Build Variants

## Project Implementation Comment

Some things have been left for future fixes, the application is the minimum to run, which provides the requested functionality.
<br><br>
Trying to use as few third party packages as possible.
<br><br>
We applied a simple MVVM architecture pattern
<br><br>
Some design patterns could be applied, like for example a factory method to perform the quotation calculations.
The only pattern that is really necessary at the moment would be a Singleton, to keep the database unique.
<br><br>
Some other data should be used inside sharedPreferences, however for speed I decided to implement everything by making direct queries to room.
<br><br>
At first I would have liked to implement it in fragments, using a sharedViewModel, however when I was building it I decided to go for more activities.
<br><br>
Some things have not been dealt with yet, adding masks, among others, but implementing them or not I believe will not change the knowledge that has already been presented.
<br><br>
<b>I look forward to your feedback! Every suggestion for improvement is welcome.</b>