// default package
// Generated Nov 24, 2014 4:55:08 PM by Hibernate Tools 4.0.0
package qa.qcri.aidr.dbmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import qa.qcri.aidr.dbmanager.entities.model.Model;
import qa.qcri.aidr.dbmanager.entities.model.ModelNominalLabel;

/**
 * Model generated by Imran
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class ModelDTO implements Serializable {

    private Long modelId;
    private Long modelFamilyId;
    private double avgPrecision;
    private double avgRecall;
    private double avgAuc;
    private int trainingCount;
    private Date trainingTime;
    private boolean isCurrentModel;
    private List<ModelNominalLabel> modelNominalLabels = null;

    public ModelDTO() {
    }

    public ModelDTO(Long modelFamilyId, double avgPrecision, double avgRecall,
            double avgAuc, int trainingCount, Date trainingTime,
            boolean isCurrentModel) {
        setModelFamilyId(modelFamilyId);
        setAvgPrecision(avgPrecision);
        setAvgRecall(avgRecall);
        setAvgAuc(avgAuc);
        setTrainingCount(trainingCount);
        setTrainingTime(trainingTime);
        setIsCurrentModel(isCurrentModel);
    }

    public Model toEntity() {
        Model model = new Model();
        model.setModelFamilyId(getModelFamilyId());
        model.setAvgPrecision(getAvgPrecision());
        model.setAvgRecall(getAvgRecall());
        model.setAvgAuc(getAvgAuc());
        model.setTrainingCount(getTrainingCount());
        model.setTrainingTime(getTrainingTime());
        model.setIsCurrentModel(isIsCurrentModel());
        return model;
    }

    public Long getModelId() {
        return this.modelId;
    }

    public void setModelId(Long modelId) {
        if (modelId == null) {
            throw new IllegalArgumentException("modelID cannot be null");
        } else if (modelId.longValue() <= 0) {
            throw new IllegalArgumentException("modelID cannot be zero or a negative number");
        } else {
            this.modelId = modelId;
        }
    }

    public Long getModelFamilyId() {
        return this.modelFamilyId;
    }

    public void setModelFamilyId(Long modelFamilyId) {
        if (modelFamilyId == null) {
            throw new IllegalArgumentException("modelFamilyID cannot be null");
        } else if (modelFamilyId.longValue() <= 0) {
            throw new IllegalArgumentException("modelFamilyID cannot be zero or a negative number");
        } else {
            this.modelFamilyId = modelFamilyId;
        }

    }

    public double getAvgPrecision() {
        return this.avgPrecision;
    }

    public void setAvgPrecision(Double avgPrecision) {
        if (avgPrecision == null) {
            throw new IllegalArgumentException("Average percision cannot be null");
        } else if (avgPrecision.doubleValue() < 0) {
            throw new IllegalArgumentException("Average percision cannot be a negative number");
        } else {
            this.avgPrecision = avgPrecision;
        }

    }

    public double getAvgRecall() {
        return this.avgRecall;
    }

    public void setAvgRecall(Double avgRecall) {
        if (avgRecall == null) {
            throw new IllegalArgumentException("Average recall cannot be null");
        } else if (avgRecall.doubleValue() < 0) {
            throw new IllegalArgumentException("Average recall cannot be a negative number");
        } else {
            this.avgRecall = avgRecall;
        }
    }

    public double getAvgAuc() {
        return this.avgAuc;
    }

    public void setAvgAuc(Double avgAuc) {
        if (avgAuc == null) {
            throw new IllegalArgumentException("Average AUC cannot be null");
        } else if (avgAuc.doubleValue() < 0) {
            throw new IllegalArgumentException("Average AUC cannot be a negative number");
        } else {
            this.avgAuc = avgAuc;
        }

    }

    public int getTrainingCount() {
        return this.trainingCount;
    }

    public void setTrainingCount(Integer trainingCount) {
        if (trainingCount == null) {
            throw new IllegalArgumentException("Average training cannot be null");
        } else if (trainingCount.doubleValue() < 0) {
            throw new IllegalArgumentException("Average training cannot be a negative number");
        } else {
            this.trainingCount = trainingCount;
        }
    }

    public Date getTrainingTime() {
        return this.trainingTime;
    }

    public void setTrainingTime(Date trainingTime) {
        if (trainingTime == null) {
            throw new IllegalArgumentException("Training time cannot be null");
        } else {
            this.trainingTime = trainingTime;
        }

    }

    public boolean isIsCurrentModel() {
        return this.isCurrentModel;
    }

    public void setIsCurrentModel(Boolean isCurrentModel) {
        if (isCurrentModel == null) {
            throw new IllegalArgumentException("isCurrentModel cannot be null");
        } else {
            this.isCurrentModel = isCurrentModel;
        }
    }

    public List<ModelNominalLabel> getModelNominalLabels() {
        //add custom propertyNotSet exaception here.
        return this.modelNominalLabels;
    }

    public void setModelNominalLabels(List<ModelNominalLabel> modelNominalLabels) {
        this.modelNominalLabels = modelNominalLabels;
    }

}
