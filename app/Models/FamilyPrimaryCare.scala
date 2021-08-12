//package Models
//
//import java.time.LocalDate
//
////import com.rallyhealth.banzai.v3.schemas.generation.{BanzaiSchemaFor, enumSchemaFor, recordSchemaFor, stringSchemaFor}
////import com.rallyhealth.chopshop.careteam.model
////import com.rallyhealth.chopshop.provider.model.{LocationId, ProviderUniqueId}
////import com.rallyhealth.spartan.v2.util.RallyEnumeration
////import com.rallyhealth.spartan.v2.util.serialization.RallyEnumerationJsonHelpers
////import com.rallyhealth.spartan.v2.util.serialization.JavaTimeFormats.{DefaultLocalDateReads, DefaultLocalDateWrites}
//import play.api.libs.json._
//
//case class FamilyPrimaryCare(
//                              members: Seq[BeneficiaryPrimaryCare],
//                              pcpDecayed: Option[Boolean] = None
//                            )
//
//object FamilyPrimaryCare {
//  implicit val format: Format[FamilyPrimaryCare] = Json.format[FamilyPrimaryCare]
//}
//
//case class BeneficiaryPrimaryCare(
//                                   beneficiaryId: String,
//                                   beneficiaryType: BeneficiaryType.ValueType,
//                                   currentPcp: Option[PrimaryCare],
//                                   intermediatePendingPcp: Option[PrimaryCare],
//                                   pendingPcp: Option[PrimaryCare]
//                                 )
//
//object BeneficiaryPrimaryCare {
//  implicit val format: Format[BeneficiaryPrimaryCare] = Json.format[BeneficiaryPrimaryCare]
//}
//
//object BeneficiaryType extends RallyEnumeration with RallyEnumerationJsonHelpers {
//  type Value = ValueType
//
//  val Subscriber: Value = Value("subscriber")
//  val Dependent: Value = Value("dependent")
//}
//
//case class PrimaryCare(
//                        providerId: Option[ProviderUniqueId],
//                        locationId: Option[LocationId],
//                        pcpId: String,
//                        effectiveDate: LocalDate,
//                        cancellationDate: Option[LocalDate],
//                        pcpDecayed: Option[Boolean] = None,
//                        pcpProviderType: PcpProviderType = PcpProviderType.Person
//                      )
//
//object PrimaryCare {
//
//  implicit val dateFormat: Format[LocalDate] = Format(DefaultLocalDateReads, DefaultLocalDateWrites)
//  implicit val format: Format[PrimaryCare] = Json.format[PrimaryCare]
//
//}
//
//sealed abstract class PcpProviderType(
//                                       override val entryName: String
//                                     ) extends EnumEntry
//
//object PcpProviderType extends PlayEnum[PcpProviderType] {
//
//  override val values = findValues
//
//  case object MedicalGroup extends PcpProviderType("MedicalGroup")
//
//  case object Person extends PcpProviderType("Person")
//
//}