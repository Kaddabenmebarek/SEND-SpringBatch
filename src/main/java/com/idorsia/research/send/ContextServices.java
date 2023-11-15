package com.idorsia.research.send;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.idorsia.research.spirit.core.conf.AppConfig;
import com.idorsia.research.spirit.core.service.ActionPatternsService;
import com.idorsia.research.spirit.core.service.AdministrationService;
import com.idorsia.research.spirit.core.service.AssayAttributeService;
import com.idorsia.research.spirit.core.service.AssayResultService;
import com.idorsia.research.spirit.core.service.AssayResultValueService;
import com.idorsia.research.spirit.core.service.AssayService;
import com.idorsia.research.spirit.core.service.AssignmentService;
import com.idorsia.research.spirit.core.service.BarcodeService;
import com.idorsia.research.spirit.core.service.BiosampleCreationHelper;
import com.idorsia.research.spirit.core.service.BiosampleEnclosureService;
import com.idorsia.research.spirit.core.service.BiosampleService;
import com.idorsia.research.spirit.core.service.BiotypeMetadataBiosampleService;
import com.idorsia.research.spirit.core.service.BiotypeMetadataService;
import com.idorsia.research.spirit.core.service.BiotypeMetadataValueService;
import com.idorsia.research.spirit.core.service.BiotypeService;
import com.idorsia.research.spirit.core.service.ContainerService;
import com.idorsia.research.spirit.core.service.DocumentService;
import com.idorsia.research.spirit.core.service.EnclosureService;
import com.idorsia.research.spirit.core.service.ExecutionDetailService;
import com.idorsia.research.spirit.core.service.ExecutionService;
import com.idorsia.research.spirit.core.service.FavoriteStudyService;
import com.idorsia.research.spirit.core.service.FoodWaterService;
import com.idorsia.research.spirit.core.service.GroupPatternService;
import com.idorsia.research.spirit.core.service.GroupService;
import com.idorsia.research.spirit.core.service.LocationLabelingService;
import com.idorsia.research.spirit.core.service.LocationService;
import com.idorsia.research.spirit.core.service.LogEntryService;
import com.idorsia.research.spirit.core.service.NamedSamplingService;
import com.idorsia.research.spirit.core.service.NamedTreatmentService;
import com.idorsia.research.spirit.core.service.ParticipantService;
import com.idorsia.research.spirit.core.service.PhaseService;
import com.idorsia.research.spirit.core.service.PlannedSampleService;
import com.idorsia.research.spirit.core.service.PpgTreatmentService;
import com.idorsia.research.spirit.core.service.PropertyLinkService;
import com.idorsia.research.spirit.core.service.PropertyService;
import com.idorsia.research.spirit.core.service.SamplingService;
import com.idorsia.research.spirit.core.service.SchedulePhaseService;
import com.idorsia.research.spirit.core.service.SpiritPropertyService;
import com.idorsia.research.spirit.core.service.StagePatternService;
import com.idorsia.research.spirit.core.service.StageService;
import com.idorsia.research.spirit.core.service.StudyActionResultService;
import com.idorsia.research.spirit.core.service.StudyActionService;
import com.idorsia.research.spirit.core.service.StudyDocumentService;
import com.idorsia.research.spirit.core.service.StudyService;
import com.idorsia.research.spirit.core.service.SubGroupPatternService;
import com.idorsia.research.spirit.core.service.SubGroupService;
import com.idorsia.research.spirit.core.service.TrackNarcoticService;
import com.idorsia.research.spirit.core.util.SpiritRights;

public class ContextServices {

	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	private static StudyService studyService;
	private static StageService stageService;
	private static PhaseService phaseService;
	private static AssayResultService assayResultService;
	private static SchedulePhaseService schedulePhaseService;
	private static AssignmentService assignmentService;
	private static AssayService assayService;
	private static ActionPatternsService actionPatternsService;
	private static SubGroupPatternService subGroupPatternService;
	private static GroupPatternService groupPatternService;
	private static StagePatternService stagePatternService;
	private static GroupService groupService;
	private static SubGroupService subGroupService;
	private static FoodWaterService foodWaterService;
	private static BiosampleService biosampleService;
	private static BiotypeService biotypeService;
	private static EnclosureService enclosureService;
	private static LocationService locationService;
	private static ContainerService containerService;
	private static AdministrationService administrationService;
	private static NamedTreatmentService namedTreatmentService;
	private static AssayResultValueService assayResultValueService;
	private static LogEntryService logEntryService;
	private static AssayAttributeService assayAttributeService;
	private static BiotypeMetadataService biotypeMetadataService;
	private static FavoriteStudyService favoriteStudyService;
	private static LocationLabelingService locationLabelingService;
	private static BiosampleCreationHelper biosampleCreationHelper;
	private static BarcodeService barcodeService;
	private static SamplingService samplingService;
	private static StudyActionService studyActionService;
	private static NamedSamplingService namedSamplingService;
	private static ParticipantService participantService;
	private static ExecutionDetailService executionDetailService;
	private static DocumentService documentService;
	private static BiosampleEnclosureService biosampleEnclosureService;
	private static StudyActionResultService studyActionResultService;
	private static StudyDocumentService studyDocumentService;
	private static BiotypeMetadataValueService biotypeMetadataValueService;
	private static PpgTreatmentService ppgTreatmentService;
	private static SpiritPropertyService spiritPropertyService;
	private static PropertyLinkService propertyLinkService;
	private static PropertyService propertyService;
	private static ExecutionService executionService;

	private static PlannedSampleService plannedSampleService;
	private static TrackNarcoticService trackNarcoticService;
	private static SpiritRights spiritRights;
	private static BiotypeMetadataBiosampleService biotypeMetadataBiosampleService;

	private static DozerBeanMapper dozerMapper;

	public static StudyService getStudyService() {
		if (studyService == null) {
			studyService = (StudyService) context.getBean("studyService");
		}
		return studyService;
	}

	public static StageService getStageService() {
		if (stageService == null) {
			stageService = (StageService) context.getBean("stageService");
		}
		return stageService;
	}

	public static PhaseService getPhaseService() {
		if (phaseService == null) {
			phaseService = (PhaseService) context.getBean("phaseService");
		}
		return phaseService;
	}

	public static GroupService getGroupService() {
		if (groupService == null) {
			groupService = (GroupService) context.getBean("groupService");
		}
		return groupService;
	}

	public static SubGroupService getSubGroupService() {
		if (subGroupService == null) {
			subGroupService = (SubGroupService) context.getBean("subGroupService");
		}
		return subGroupService;
	}

	public static FoodWaterService getFoodWaterService() {
		if (foodWaterService == null) {
			foodWaterService = (FoodWaterService) context.getBean("foodWaterService");
		}
		return foodWaterService;
	}

	public static AssignmentService getAssignmentService() {
		if (assignmentService == null) {
			assignmentService = (AssignmentService) context.getBean("assignmentService");
		}
		return assignmentService;
	}

	public static BiosampleService getBiosampleService() {
		if (biosampleService == null) {
			biosampleService = (BiosampleService) context.getBean("biosampleService");
		}
		return biosampleService;
	}

	public static EnclosureService getEnclosureService() {
		if (enclosureService == null) {
			enclosureService = (EnclosureService) context.getBean("enclosureService");
		}
		return enclosureService;
	}

	public static AssayResultService getAssayResultService() {
		if (assayResultService == null) {
			assayResultService = (AssayResultService) context.getBean("assayResultService");
		}
		return assayResultService;
	}

	public static SchedulePhaseService getSchedulePhaseService() {
		if (schedulePhaseService == null) {
			schedulePhaseService = (SchedulePhaseService) context.getBean("schedulePhaseService");
		}
		return schedulePhaseService;
	}

	public static ActionPatternsService getActionPatternsService() {
		if (actionPatternsService == null) {
			actionPatternsService = (ActionPatternsService) context.getBean("actionPatternsService");
		}
		return actionPatternsService;
	}

	public static SubGroupPatternService getSubGroupPatternService() {
		if (subGroupPatternService == null) {
			subGroupPatternService = (SubGroupPatternService) context.getBean("subGroupPatternService");
		}
		return subGroupPatternService;
	}

	public static GroupPatternService getGroupPatternService() {
		if (groupPatternService == null) {
			groupPatternService = (GroupPatternService) context.getBean("groupPatternService");
		}
		return groupPatternService;
	}

	public static StagePatternService getStagePatternService() {
		if (stagePatternService == null) {
			stagePatternService = (StagePatternService) context.getBean("stagePatternService");
		}
		return stagePatternService;
	}

	public static AssayService getAssayService() {
		if (assayService == null) {
			assayService = (AssayService) context.getBean("assayService");
		}
		return assayService;
	}

	public static AdministrationService getAdministrationService() {
		if(administrationService == null) {
			administrationService = (AdministrationService) context.getBean("administrationService");
		}
		return administrationService;
	}

	public static NamedTreatmentService getNamedTreatmentService() {
		if(namedTreatmentService == null ) {
			namedTreatmentService = (NamedTreatmentService) context.getBean("namedTreatmentService");
		}
		return namedTreatmentService;
	}

	public static FavoriteStudyService getFavoriteStudyService() {
		if(favoriteStudyService == null ) {
			favoriteStudyService = (FavoriteStudyService) context.getBean("favoriteStudyService");
		}
		return favoriteStudyService;
	}
	
	public static BiotypeService getBiotypeService() {
		if(biotypeService == null) {
			biotypeService = (BiotypeService) context.getBean("biotypeService");
		}
		return biotypeService;
	}

	public static DozerBeanMapper getDozerMapper() {
		if(dozerMapper == null) {
			dozerMapper = (DozerBeanMapper) context.getBean("org.dozer.Mapper");
		}
		return dozerMapper;
	}

	public static AssayResultValueService getAssayResultValueService() {
		if(assayResultValueService == null) {
			assayResultValueService = (AssayResultValueService) context.getBean("assayResultValueService");
		}
		return assayResultValueService;
	}

	public static LogEntryService getLogEntryService() {
		if(logEntryService == null) {
			logEntryService = (LogEntryService) context.getBean("logEntryService");
		}
		return logEntryService;
	}

	public static BiosampleCreationHelper getBiosampleCreationHelper() {
		if(biosampleCreationHelper == null) {
			biosampleCreationHelper = (BiosampleCreationHelper) context.getBean("biosampleCreationHelper");
		}
		return biosampleCreationHelper;
	}

	public static AssayAttributeService getAssayAttributeService() {
		if(assayAttributeService == null) {
			assayAttributeService = (AssayAttributeService) context.getBean("assayAttributeService");
		}
		return assayAttributeService;
	}

	public static BiotypeMetadataService getBiotypeMetadataService() {
		if(biotypeMetadataService == null) {
			biotypeMetadataService = (BiotypeMetadataService) context.getBean("biotypeMetadataService");
		}
		return biotypeMetadataService;
	}
	
	public static LocationService getLocationService() {
		if(locationService == null) {
			locationService = (LocationService) context.getBean("locationService");
		}
		return locationService;
	}
	
	public static ContainerService getContainerService() {
		if(containerService == null) {
			containerService = (ContainerService) context.getBean("containerService");
		}
		return containerService;
	}
	
	public static LocationLabelingService getLocationLabelingService() {
		if(locationLabelingService == null) {
			locationLabelingService = (LocationLabelingService) context.getBean("locationLabelingService");
		}
		return locationLabelingService;
	}

	public static BarcodeService getBarcodeService() {
		if(barcodeService == null) {
			barcodeService = (BarcodeService) context.getBean("barcodeService");
		}
		return barcodeService;
	}

	public static SamplingService getSamplingService() {
		if(samplingService == null) {
			samplingService = (SamplingService) context.getBean("samplingService");
		}
		return samplingService;
	}

	public static StudyActionService getStudyActionService() {
		if(studyActionService == null) {
			studyActionService = (StudyActionService) context.getBean("studyActionService");
		}
		return studyActionService;
	}

	public static NamedSamplingService getNamedSamplingService() {
		if(namedSamplingService == null) {
			namedSamplingService = (NamedSamplingService) context.getBean("namedSamplingService");
		}
		return namedSamplingService;
	}

	public static ParticipantService getParticipantService() {
		if(participantService == null) {
			participantService = (ParticipantService) context.getBean("participantService");
		}
		return participantService;
	}

	public static ExecutionDetailService getExecutionDetailService() {
		if(executionDetailService == null) {
			executionDetailService  = (ExecutionDetailService) context.getBean("executionDetailService");
		}
		return executionDetailService ;
	}

	public static DocumentService getDocumentService() {
		if(documentService == null) {
			documentService  = (DocumentService) context.getBean("documentService");
		}
		return documentService ;
	}

	public static BiosampleEnclosureService getBiosampleEnclosureService() {
		if(biosampleEnclosureService == null) {
			biosampleEnclosureService  = (BiosampleEnclosureService) context.getBean("biosampleEnclosureService");
		}
		return biosampleEnclosureService ;
	}

	public static StudyActionResultService getActionResultService() {
		if(studyActionResultService == null) {
			studyActionResultService  = (StudyActionResultService) context.getBean("studyActionResultService");
		}
		return studyActionResultService ;
	}

	public static StudyDocumentService getStudyDocumentService() {
		if(studyDocumentService == null) {
			studyDocumentService  = (StudyDocumentService) context.getBean("studyDocumentService");
		}
		return studyDocumentService ;
	}

	public static BiotypeMetadataValueService getBiotypeMetadataValueService() {
		if(biotypeMetadataValueService == null) {
			biotypeMetadataValueService  = (BiotypeMetadataValueService) context.getBean("biotypeMetadataValueService");
		}
		return biotypeMetadataValueService ;
	}

	public static PpgTreatmentService getPpgTreatmentService() {
		if(ppgTreatmentService == null) {
			ppgTreatmentService  = (PpgTreatmentService) context.getBean("ppgTreatmentService");
		}
		return ppgTreatmentService ;
	}

	public static ExecutionService getExecutionService() {
		if(executionService == null) {
			executionService  = (ExecutionService) context.getBean("executionService");
		}
		return executionService ;
	}
	
	public static SpiritPropertyService getSpiritPropertyService() {
		if(spiritPropertyService == null) {
			spiritPropertyService  = (SpiritPropertyService) context.getBean("spiritPropertyService");
		}
		return spiritPropertyService ;
	}

	public static PropertyLinkService getPropertyLinkService() {
		if(propertyLinkService == null) {
			propertyLinkService  = (PropertyLinkService) context.getBean("propertyLinkService");
		}
		return propertyLinkService ;
	}

	public static PropertyService getPropertyService() {
		if(propertyService == null) {
			propertyService  = (PropertyService) context.getBean("propertyService");
		}
		return propertyService ;
	}

	public static TrackNarcoticService getTrackNarcoticService() {
		if(trackNarcoticService == null) {
			trackNarcoticService  = (TrackNarcoticService) context.getBean("trackNarcoticService");
		}
		return trackNarcoticService;
	}

	public static SpiritRights getSpiritRights() {
		if(spiritRights == null) {
			spiritRights  = (SpiritRights) context.getBean("spiritRights");
		}
		return spiritRights;
	}

	public static BiotypeMetadataBiosampleService getBiotypeMetadataBiosampleService() {
		if(biotypeMetadataBiosampleService == null) {
			biotypeMetadataBiosampleService  = (BiotypeMetadataBiosampleService) context.getBean("biotypeMetadataBiosampleService");
		}
		return biotypeMetadataBiosampleService;
	}

	public static PlannedSampleService getPlannedSampleService() {
		if(plannedSampleService == null) {
			plannedSampleService  = (PlannedSampleService) context.getBean("plannedSampleService ");
		}
		return plannedSampleService ;
	}
	
	
	
}
