package org.apromore.apmlog.filter.rules.desc;

import org.apromore.apmlog.filter.rules.LogFilterRule;
import org.apromore.apmlog.filter.rules.RuleValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CaseSectionCaseAttributeDesc {

    public static String getDescription(LogFilterRule logFilterRule) {

        String desc = "";
        String choice = logFilterRule.getChoice().toString().toLowerCase();
        desc += choice.substring(0, 1).toUpperCase() + choice.substring(1) + " all cases containing an event where ";

        String attributeKey = logFilterRule.getKey();

        desc += "'" + attributeKey + "' is equal to [";

        Set<RuleValue> ruleValues = logFilterRule.getPrimaryValues();
        List<RuleValue> ruleValueList = new ArrayList<RuleValue>(ruleValues);
        Collections.sort(ruleValueList);

        for (int i = 0; i < ruleValueList.size(); i++) {
            desc += ruleValueList.get(i).getStringValue();
            if (i < ruleValueList.size() -1) desc += " OR ";
        }

        desc += "]";

        return desc;
    }
}
