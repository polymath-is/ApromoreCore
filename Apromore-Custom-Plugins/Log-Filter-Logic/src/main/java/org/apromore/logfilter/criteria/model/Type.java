package org.apromore.logfilter.criteria.model;

/**
 * 
 * @author Bruce Nguyen
 *
 */
public enum Type {
    CONCEPT_NAME, CASE_VARIANT, DIRECT_FOLLOW, EVENTUAL_FOLLOW, LIFECYCLE_TRANSITION,
    ORG_GROUP, ORG_RESOURCE, ORG_ROLE,
//    TIME_DURATION,
    TIME_TIMESTAMP, DURATION_RANGE,
    TIME_STARTRANGE, TIME_ENDRANGE, //2019-09-20
    UNKNOWN
}
