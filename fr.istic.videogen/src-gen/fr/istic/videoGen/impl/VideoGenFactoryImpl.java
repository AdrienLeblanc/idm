/**
 * generated by Xtext 2.15.0
 */
package fr.istic.videoGen.impl;

import fr.istic.videoGen.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VideoGenFactoryImpl extends EFactoryImpl implements VideoGenFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static VideoGenFactory init()
  {
    try
    {
      VideoGenFactory theVideoGenFactory = (VideoGenFactory)EPackage.Registry.INSTANCE.getEFactory(VideoGenPackage.eNS_URI);
      if (theVideoGenFactory != null)
      {
        return theVideoGenFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new VideoGenFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VideoGenFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case VideoGenPackage.VIDEO_GENERATOR_MODEL: return createVideoGeneratorModel();
      case VideoGenPackage.VIDEO_GEN_INFORMATION: return createVideoGenInformation();
      case VideoGenPackage.MEDIA: return createMedia();
      case VideoGenPackage.MANDATORY_MEDIA: return createMandatoryMedia();
      case VideoGenPackage.OPTIONAL_MEDIA: return createOptionalMedia();
      case VideoGenPackage.ALTERNATIVES_MEDIA: return createAlternativesMedia();
      case VideoGenPackage.MEDIA_DESCRIPTION: return createMediaDescription();
      case VideoGenPackage.IMAGE_DESCRIPTION: return createImageDescription();
      case VideoGenPackage.VIDEO_DESCRIPTION: return createVideoDescription();
      case VideoGenPackage.VIDEO_TEXT: return createVideoText();
      case VideoGenPackage.FILTER: return createFilter();
      case VideoGenPackage.BLACK_WHITE_FILTER: return createBlackWhiteFilter();
      case VideoGenPackage.NEGATE_FILTER: return createNegateFilter();
      case VideoGenPackage.FLIP_FILTER: return createFlipFilter();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VideoGeneratorModel createVideoGeneratorModel()
  {
    VideoGeneratorModelImpl videoGeneratorModel = new VideoGeneratorModelImpl();
    return videoGeneratorModel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VideoGenInformation createVideoGenInformation()
  {
    VideoGenInformationImpl videoGenInformation = new VideoGenInformationImpl();
    return videoGenInformation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Media createMedia()
  {
    MediaImpl media = new MediaImpl();
    return media;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MandatoryMedia createMandatoryMedia()
  {
    MandatoryMediaImpl mandatoryMedia = new MandatoryMediaImpl();
    return mandatoryMedia;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OptionalMedia createOptionalMedia()
  {
    OptionalMediaImpl optionalMedia = new OptionalMediaImpl();
    return optionalMedia;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AlternativesMedia createAlternativesMedia()
  {
    AlternativesMediaImpl alternativesMedia = new AlternativesMediaImpl();
    return alternativesMedia;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MediaDescription createMediaDescription()
  {
    MediaDescriptionImpl mediaDescription = new MediaDescriptionImpl();
    return mediaDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ImageDescription createImageDescription()
  {
    ImageDescriptionImpl imageDescription = new ImageDescriptionImpl();
    return imageDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VideoDescription createVideoDescription()
  {
    VideoDescriptionImpl videoDescription = new VideoDescriptionImpl();
    return videoDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VideoText createVideoText()
  {
    VideoTextImpl videoText = new VideoTextImpl();
    return videoText;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Filter createFilter()
  {
    FilterImpl filter = new FilterImpl();
    return filter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BlackWhiteFilter createBlackWhiteFilter()
  {
    BlackWhiteFilterImpl blackWhiteFilter = new BlackWhiteFilterImpl();
    return blackWhiteFilter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NegateFilter createNegateFilter()
  {
    NegateFilterImpl negateFilter = new NegateFilterImpl();
    return negateFilter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FlipFilter createFlipFilter()
  {
    FlipFilterImpl flipFilter = new FlipFilterImpl();
    return flipFilter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VideoGenPackage getVideoGenPackage()
  {
    return (VideoGenPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static VideoGenPackage getPackage()
  {
    return VideoGenPackage.eINSTANCE;
  }

} //VideoGenFactoryImpl