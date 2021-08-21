package dev.abel.springbootdocker.collections.country;

import dev.abel.springbootdocker.generics.StateCoverage;
import dev.abel.springbootdocker.utils.GenerateUUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Data
public class CoverageCountry {

    @Id
    private String id;

    @Field( "totalMarketIndex")
    @NonNull
    private Integer totalMarketIndex;

    @Field( "totalShares")
    @NonNull
    private Integer totalShares;

    @Field( "totalCoverage")
    private Integer totalCoverage;

    @Field( "coverageMarketIndex")
    private Integer coverageMarketIndex;

    @Field( "coverageShares")
    private Integer coverageShares;

    @Field( "isCovered")
    private Boolean isCovered = false;

    @Field( "state")
    private StateCoverage state;

    @Field( "lastScrapedAt")
    @NonNull
    private Date lastScrapedAt;

    private CoverageCountry(@NonNull Integer marketIndexes, @NonNull Integer shares, StateCoverage state) {
        this.id = GenerateUUID.generateUniqueId();
        this.totalMarketIndex = marketIndexes;
        this.totalShares = shares;
        this.state = state;
        this.lastScrapedAt = new Date();
    }

    public static CoverageCountry createNewCoverage(){
         CoverageCountry coverageCountry = new CoverageCountry(0,0, StateCoverage.NEW);
         coverageCountry.totalCoverage = 0;
        return coverageCountry;
    }

    public static CoverageCountry createBaseCoverage(Integer totalMarketIndex, Integer totalShares){
        CoverageCountry coverageCountry = new CoverageCountry(totalMarketIndex,totalShares, StateCoverage.BASE);
        coverageCountry.totalCoverage = 0;
        return coverageCountry;
    }

    public static CoverageCountry createUpdatedCoverage(Integer totalMarketIndex, Integer totalShares, CoverageCountry coverageBase) throws Exception {
        CoverageCountry coverageCountry = new CoverageCountry(totalMarketIndex,totalShares, StateCoverage.UPDATED);
        coverageCountry.compareAndFill(coverageBase);
        return coverageCountry;
    }

    private void compareAndFill(CoverageCountry coverageBase) throws Exception {
        if(coverageBase.state != StateCoverage.BASE)
            throw new Exception("Comparing with a not coverage BASE");

        this.generateMarketIndexCoverage(coverageBase);
        this.generateShareCoverage(coverageBase);
    }


    private void generateShareCoverage(CoverageCountry coverageCountryBase) throws Exception {
        if(this.state != StateCoverage.UPDATED)
            throw new Exception("This is not updated coverage, you can't update this");

        if(coverageCountryBase.getTotalShares().equals(this.totalShares))
            this.coverageShares = 100;

        if(coverageCountryBase.getTotalShares() > this.totalShares) {
            this.coverageShares = (this.totalShares * 100) / coverageCountryBase.totalShares;
            this.totalShares = coverageCountryBase.totalShares;
        }
        if(coverageCountryBase.getTotalShares() < this.totalShares)
            this.coverageShares = coverageCountryBase.totalShares  * 100 / this.totalShares;

        setTotalCoverage();
    }

    private void generateMarketIndexCoverage(CoverageCountry coverageCountryBase) throws Exception {
        if(this.state != StateCoverage.UPDATED)
            throw new Exception("This is not updated coverage, you can't update this");

        if(coverageCountryBase.totalMarketIndex.equals(this.totalMarketIndex))
            this.coverageMarketIndex = 100;

        if(coverageCountryBase.totalMarketIndex > this.totalMarketIndex) {
            this.coverageMarketIndex = this.totalMarketIndex * 100 / coverageCountryBase.coverageMarketIndex;
            this.totalMarketIndex = coverageCountryBase.totalMarketIndex;
        }

        if(coverageCountryBase.totalMarketIndex < this.totalMarketIndex)
            this.coverageMarketIndex = coverageCountryBase.totalMarketIndex  * 100 / this.totalMarketIndex;

        setTotalCoverage();
    }

    public void setTotalCoverage(){
        if(this.coverageShares == 0 || this.coverageMarketIndex == 0) {
            this.totalCoverage = 0;
            this.isCovered = false;
        }

        this.totalCoverage = (this.coverageShares + this.coverageMarketIndex) / 2;
        if(this.totalCoverage >= 90)
            this.isCovered = true;
    }



}

