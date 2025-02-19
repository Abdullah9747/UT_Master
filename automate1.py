import os
import subprocess
import re
import javalang
import pandas as pd
import random
import string
import numpy as np








class Driver:
    def __init__(self):
        self.jqf=JQF()
        self.spf=SPF()

    def prepare_JQf(self,function_code):
        class_name,method,params=self.jqf.make_AST(function_code)
        self.jqf.make_JQF_file(class_name,function_code)
        self.jqf.generate_test_file(class_name,params,method)
    def driver(self,file_name):
        jqf_file_name = f"{file_name}Test"
        self.jqf.run_jqf(jqf_file_name)



function1="""public class TrafficLight {
    public static int calculateGreenLightTime(int roadLength, int trafficDensity, int avgSpeed, boolean isPeakHour) {
        if (roadLength <= 0 || trafficDensity <= 0 || avgSpeed <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        int baseTime = roadLength / avgSpeed; // Basic green light time
        if (isPeakHour) {
            baseTime += (trafficDensity / 10); // Increase time during peak hours
        } else if (trafficDensity > 50) {
            baseTime += (trafficDensity / 20); // Increase time for higher density
        } else {
            baseTime = Math.max(baseTime - 5, 10); // Minimum time during low density
        }
        return baseTime;
    }

}"""

function2="""public class WaterUsage {
    public static double calculateWaterUsage(int familyMembers, int appliances, boolean hasGarden, int dailyUseLiters) {
        double baseUsage = familyMembers * dailyUseLiters;
        if (hasGarden) {
            baseUsage += 50; // Additional for garden
        }
        if (appliances > 0) {
            baseUsage += appliances * 10; // Additional for appliances
        }
        if (familyMembers > 5) {
            baseUsage *= 1.1; // Slight increase for larger families
        }
        return baseUsage;
    }

}"""

function3="""public class ElectricityBill {
    public static double calculateBill(int unitsConsumed, double ratePerUnit, boolean isPeakMonth) {
        if (unitsConsumed < 0 || ratePerUnit <= 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
        double bill = unitsConsumed * ratePerUnit;
        if (isPeakMonth) {
            bill *= 1.2; // Surcharge during peak months
        } 
        if (unitsConsumed > 500) {
            bill += 50; // Additional fixed charge for high usage
        } else if (unitsConsumed < 100) {
            bill *= 0.9; // Discount for low usage
        }
        return bill;
    }

}"""
function4="""public class TaxCalculator {
    public static double calculateTax(double income, int dependents, boolean hasInvestments) {
        if (income < 0 || dependents < 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
        double taxRate = income > 100000 ? 0.3 : income > 50000 ? 0.2 : 0.1;
        double baseTax = income * taxRate;
        if (dependents > 0) {
            baseTax -= dependents * 2000; // Deduction per dependent
        }
        if (hasInvestments) {
            baseTax *= 0.85; // Investment rebate
        }
        return Math.max(baseTax, 0); // Ensure tax is not negative
    }

}"""
driver_object=Driver()
driver_object.prepare_JQf(function1)
driver_object.driver("WaterUsage")