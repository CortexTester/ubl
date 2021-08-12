package cbx.ubl

import java.time.LocalDate
import java.time.LocalTime

data class Order(
    val CbxVersionID: String? = "0.1",
    val OrderNumber: String,
    val IssueDate: LocalDate,
    val IssueTime: LocalTime,
    val DocumentCurrencyCode: CurrencyCode? = CurrencyCode.USD,
    val BuyerCustomerParty: Party,
    val SellerSupplierParty: Party,
    val OrderLine: List<LineItem>,

    val AccountingCostCode: List<Code>? = null,
    val AdditionalDocumentReference: List<DocumentReference>? = null,
    val AllowanceCharge: List<AllowanceCharge>? = null,
    val AnticipatedMonetaryTotal: MonetaryTotal? = null,
    val Contract: List<Contract>? = null,
    val CustomizationID: List<Id>?=null,
    val Delivery: List<Delivery>? = null,
    val Note: String? = null,
    val OrderDocumentReference: List<DocumentReference>? = null,
    val OriginatorCustomerParty: Party? = null,
    val OriginatorDocumentReference: List<DocumentReference>? = null,
    val QuotationDocumentReference: List<DocumentReference>? = null,
    val TaxTotal: List<TaxTotal>? = null,
    val ValidityPeriod: Period? = null,


)

data class Invoice(
    val AccountingCost: String?,
    val AccountingCustomerParty: Party,
    val AccountingSupplierParty: Party,
    val AdditionalDocumentReference: List<DocumentReference>,
    val AllowanceCharge: AllowanceCharge,
    val ContractDocumentReference: List<DocumentReference>,
    val Delivery: Delivery,
    val DocumentCurrencyCode: Code,
    val ID: List<Id>? = null, //would be OrderNumber
    val InvoiceLine: List<LineItem>,
    val InvoicePeriod: Period,
    val InvoiceTypeCode: List<Code>,
    val IssueDate: String,
    val LegalMonetaryTotal: List<MonetaryTotal>,
    val Note: String,
    val OrderReference: List<DocumentReference>,
    val PayeeParty: Party,
    val PaymentMeans: PaymentMean,
    val PaymentTerms: String,
    val TaxPointDate: String,
    val TaxTotal: List<TaxTotal>,
    val CbxVersionID: String,


)
