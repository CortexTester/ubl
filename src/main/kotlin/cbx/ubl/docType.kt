package cbx.ubl

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Order(
    val cbxVersionId: String? = "0.1",
    val orderNumber: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val issueDateTime: LocalDateTime,
    val documentCurrencyCode: CurrencyCode? = CurrencyCode.USD,
    val buyerCustomerParty: Party,
    val sellerSupplierParty: Party,
    val orderLine: List<LineItem>,

    val accountingCostCode: List<Code>? = null,
    val additionalDocumentReference: List<DocumentReference>? = null,
    val allowanceCharge: List<AllowanceCharge>? = null,
    val anticipatedMonetaryTotal: MonetaryTotal? = null,
    val contract: List<Contract>? = null,
    val customizationId: List<Id>?=null,
    val delivery: List<Delivery>? = null,
    val note: String? = null,
    val orderDocumentReference: List<DocumentReference>? = null,
    val originatorCustomerParty: Party? = null,
    val originatorDocumentReference: List<DocumentReference>? = null,
    val quotationDocumentReference: List<DocumentReference>? = null,
    val taxTotal: List<TaxTotal>? = null,
    val validityPeriod: Period? = null,


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
    val Id: List<Id>? = null, //would be OrderNumber
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
    val CbxVersionId: String,


)
