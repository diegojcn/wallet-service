# wallet-service

This is a simple version of a service that will support
operations for depositing, withdrawing, and transferring funds between users.

● Create Wallet: Allow the creation of wallets for users. http://localhost:8080/api/wallet/createWallet?owner=diegojcn

● Retrieve Balance: Retrieve the current balance of a user's wallet. http://localhost:8080/api/wallet/balance?owner=diegojcn

● Retrieve Historical Balance: Retrieve the balance of a user's wallet at a specific
point in the past. http://localhost:8080/api/wallet/historical-balance?owner=diegojcn&startDate=2024-12-29&endDate=2024-12-30

● Deposit Funds: Enable users to deposit money into their wallets. http://localhost:8080/api/wallet/deposit?owner=diegojcn&amount=1000

● Withdraw Funds: Enable users to withdraw money from their wallets.
http://localhost:8080/api/wallet/withdraw?owner=diegojcn&amount=150

● Transfer Funds: Facilitate the transfer of money between user wallets. http://localhost:8080/api/wallet/transfer?fromOwner=diegojcn&amount=50&toOwner=barbara