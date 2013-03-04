/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.impl;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.management.ActivityStatistics;
import org.activiti.engine.management.ActivityStatisticsQuery;

public class ActivityStatisticsQueryImpl extends
    AbstractQuery<ActivityStatisticsQuery, ActivityStatistics> implements ActivityStatisticsQuery{

  protected static final long serialVersionUID = 1L;
  protected boolean includeFailedJobs = false;
  protected String processDefinitionId;
  
  public ActivityStatisticsQueryImpl(String processDefinitionId, CommandExecutor executor) {
    super(executor);
    this.processDefinitionId = processDefinitionId;
  }
  
  public long executeCount(CommandContext commandContext) {
    checkQueryOk();
    return 
      commandContext
        .getStatisticsManager()
        .getStatisticsCountGroupedByActivity(this);
  }

  public List<ActivityStatistics> executeList(
      CommandContext commandContext, Page page) {
    checkQueryOk();
    return 
      commandContext
        .getStatisticsManager()
        .getStatisticsGroupedByActivity(this, page);
  }
  
  protected void checkQueryOk() {
    super.checkQueryOk();
    if (processDefinitionId == null) {
      throw new ActivitiException("No valid process definition id supplied.");
    }
  }

  public ActivityStatisticsQuery includeFailedJobs() {
    includeFailedJobs = true;
    return this;
  }

  public boolean isFailedJobsToInclude() {
    return includeFailedJobs;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }
}
