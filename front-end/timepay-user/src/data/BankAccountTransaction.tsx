export interface BankAccountTransaction {
  id: number;
  bankAccountId: number;
  code: string;
  amount: number;
  status: string;
  receiverAccountNumber: string;
  senderAccountNumber: string;
  balanceSnapshot: number;
  transactionAt: string;
  createdAt: string;
  updatedAt: string;
}
