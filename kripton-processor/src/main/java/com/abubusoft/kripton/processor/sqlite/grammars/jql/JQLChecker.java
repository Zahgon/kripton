/**
 * ****************************************************************************
 *  Copyright 2015, 2017 Francesco Benincasa (info@abubusoft.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
/**
 */
package com.abubusoft.kripton.processor.sqlite.grammars.jql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.common.Triple;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.Finder;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLDynamicStatementType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLPlaceHolder.JQLPlaceHolderType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection.ProjectionBuilder;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection.ProjectionType;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlLexer;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Bind_dynamic_sqlContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Bind_parameterContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Column_aliasContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Column_fully_qualified_nameContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Column_nameContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Column_name_setContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Column_name_to_updateContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Column_value_setContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Columns_to_updateContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Group_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Having_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Limit_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Offset_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Order_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Projected_columnsContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Result_columnContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Select_coreContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Select_or_valuesContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Table_nameContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmt_in_clauseContext;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;

/**
 * The Class JQLChecker.
 */
public class JQLChecker {

    /**
     * The instance.
     */
    protected static JQLChecker instance;

    public static final JQLChecker getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The walker.
     */
    ParseTreeWalker walker = new ParseTreeWalker();

    /**
     * Instantiates a new JQL checker.
     */
    private JQLChecker() {
    }

    protected <L extends JqlBaseListener> void analyzeInternal(JQLContext jqlContext, final String jql, L listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected <L extends JqlBaseListener> void analyzeVariableStatementInternal(JQLContext jqlContext, final String jql, L listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <L extends JqlBaseListener> void analyze(final JQLContext jqlContext, final JQL jql, L listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Pair<ParserRuleContext, CommonTokenStream> prepareParser(final JQLContext jqlContext, final String jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Pair<ParserRuleContext, CommonTokenStream> prepareVariableStatement(final JQLContext jqlContext, final String jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<JQLProjection> extractProjections(final JQLContext jqlContext, String jqlValue, final Finder<SQLProperty> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<String> extractColumnsToInsertOrUpdate(final JQLContext jqlContext, String jqlValue, final Finder<SQLProperty> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String replaceFromVariableStatement(JQLContext context, String jql, final JQLReplacerListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The replace.
     */
    List<Triple<Token, Token, String>> replace = new ArrayList<>();

    /**
     * The listener interface for receiving JQLRewriter events. The class that is interested in processing a JQLRewriter event implements this interface, and the object created
     * with that class is registered with a component using the component's <code>addJQLRewriterListener</code> method. When the JQLRewriter event occurs, that object's appropriate
     * method is invoked.
     *
     * @author Francesco Benincasa (info@abubusoft.com)
     */
    public class JQLRewriterListener extends JqlBaseListener {

        private boolean inStatement = false;

        /**
         * The listener.
         */
        private JQLReplacerListener listener;

        public void init(JQLReplacerListener listener) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterTable_name(com.abubusoft.kripton.processor.sqlite.grammars.jsql.
		 * JqlParser.Table_nameContext)
		 */
        @Override
        public void enterTable_name(Table_nameContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterBind_parameter(com.abubusoft.kripton.processor.sqlite.grammars.
		 * jsql.JqlParser.Bind_parameterContext)
		 */
        @Override
        public void enterBind_parameter(Bind_parameterContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterColumn_name_to_update(com.abubusoft.kripton.processor.sqlite.
		 * grammars.jsql.JqlParser.Column_name_to_updateContext)
		 */
        @Override
        public void enterColumn_name_to_update(Column_name_to_updateContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterColumn_fully_qualified_name(com.abubusoft.kripton.processor.
		 * sqlite.grammars.jsql.JqlParser.Column_fully_qualified_nameContext)
		 */
        @Override
        public void enterColumn_fully_qualified_name(Column_fully_qualified_nameContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterColumn_name(com.abubusoft.kripton.processor.sqlite.grammars.jsql
		 * .JqlParser.Column_nameContext)
		 */
        @Override
        public void enterColumn_name(Column_nameContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void enterColumn_alias(Column_aliasContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterBind_dynamic_sql(com.abubusoft.kripton.processor.sqlite.grammars
		 * .jsql.JqlParser.Bind_dynamic_sqlContext)
		 */
        @Override
        public void enterBind_dynamic_sql(Bind_dynamic_sqlContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterWhere_stmt(com.abubusoft.kripton.processor.sqlite.grammars.jsql.
		 * JqlParser.Where_stmtContext)
		 */
        @Override
        public void enterWhere_stmt(Where_stmtContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# exitWhere_stmt(com.abubusoft.kripton.processor.sqlite.grammars.jsql.
		 * JqlParser.Where_stmtContext)
		 */
        @Override
        public void exitWhere_stmt(Where_stmtContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterColumn_name_set(com.abubusoft.kripton.processor.sqlite.grammars.
		 * jsql.JqlParser.Column_name_setContext)
		 */
        @Override
        public void enterColumn_name_set(Column_name_setContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# exitColumn_name_set(com.abubusoft.kripton.processor.sqlite.grammars.
		 * jsql.JqlParser.Column_name_setContext)
		 */
        @Override
        public void exitColumn_name_set(Column_name_setContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# enterColumn_value_set(com.abubusoft.kripton.processor.sqlite.grammars
		 * .jsql.JqlParser.Column_value_setContext)
		 */
        @Override
        public void enterColumn_value_set(Column_value_setContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener# exitColumn_value_set(com.abubusoft.kripton.processor.sqlite.grammars.
		 * jsql.JqlParser.Column_value_setContext)
		 */
        @Override
        public void exitColumn_value_set(Column_value_setContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void enterWhere_stmt_in_clause(Where_stmt_in_clauseContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void exitWhere_stmt_in_clause(Where_stmt_in_clauseContext ctx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public String replace(final JQLContext jqlContext, JQL jql, final JQLReplacerListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String replace(final JQLContext jqlContext, String jql, final JQLReplacerListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replace internal.
     *
     * @param jqlContext
     *            the jql context
     * @param jql
     *            the jql
     * @param replace
     *            the replace
     * @param rewriterListener
     *            the rewriter listener
     * @return the string
     */
    private String replaceInternal(final JQLContext jqlContext, String jql, final List<Triple<Token, Token, String>> replace, JqlBaseListener rewriterListener) {
        Pair<ParserRuleContext, CommonTokenStream> parser = prepareParser(jqlContext, jql);
        walker.walk(rewriterListener, parser.value0);
        TokenStreamRewriter rewriter = new TokenStreamRewriter(parser.value1);
        for (Triple<Token, Token, String> item : replace) {
            rewriter.replace(item.value0, item.value1, item.value2);
        }
        return rewriter.getText();
    }

    /**
     * Replace from variable statement internal.
     *
     * @param context
     *            the context
     * @param jql
     *            the jql
     * @param replace
     *            the replace
     * @param rewriterListener
     *            the rewriter listener
     * @return the string
     */
    private String replaceFromVariableStatementInternal(JQLContext context, String jql, final List<Triple<Token, Token, String>> replace, JqlBaseListener rewriterListener) {
        Pair<ParserRuleContext, CommonTokenStream> parser = prepareVariableStatement(context, jql);
        walker.walk(rewriterListener, parser.value0);
        TokenStreamRewriter rewriter = new TokenStreamRewriter(parser.value1);
        for (Triple<Token, Token, String> item : replace) {
            rewriter.replace(item.value0, item.value1, item.value2);
        }
        return rewriter.getText();
    }

    /**
     * The Class JQLParameterName.
     */
    public static class JQLParameterName {

        /**
         * Instantiates a new JQL parameter name.
         *
         * @param value
         *            the value
         */
        private JQLParameterName(String value) {
            values = value.split("\\.");
        }

        /**
         * The values.
         */
        private String[] values;

        public String getValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public String getBeanName() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isNested() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public static JQLParameterName parse(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public void verify(final JQLContext jqlContext, final JQL jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<JQLPlaceHolder> extractPlaceHoldersAsList(final JQLContext jqlContext, String jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<JQLPlaceHolder> extractPlaceHoldersAsSet(final JQLContext jqlContext, String jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<JQLPlaceHolder> extractPlaceHoldersFromVariableStatementAsSet(JQLContext jqlContext, String jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<JQLPlaceHolder> extractFromVariableStatement(JQLContext jqlContext, String jql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extract place holders.
     *
     * @param <L>
     *            the generic type
     * @param jqlContext
     *            the jql context
     * @param jql
     *            the jql
     * @param result
     *            the result
     * @return the l
     */
    private <L extends Collection<JQLPlaceHolder>> L extractPlaceHolders(final JQLContext jqlContext, String jql, final L result) {
        final One<Boolean> valid = new One<>();
        valid.value0 = false;
        analyzeInternal(jqlContext, jql, new JqlBaseListener() {

            @Override
            public void enterBind_parameter(Bind_parameterContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void enterBind_dynamic_sql(Bind_dynamic_sqlContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        return result;
    }

    /**
     * Extract place holders from variable statement.
     *
     * @param <L>
     *            the generic type
     * @param jqlContext
     *            the jql context
     * @param jql
     *            the jql
     * @param result
     *            the result
     * @return the l
     */
    private <L extends Collection<JQLPlaceHolder>> L extractPlaceHoldersFromVariableStatement(final JQLContext jqlContext, String jql, final L result) {
        final One<Boolean> valid = new One<>();
        if (!StringUtils.hasText(jql))
            return result;
        valid.value0 = false;
        analyzeVariableStatementInternal(jqlContext, jql, new JqlBaseListener() {

            @Override
            public void enterBind_parameter(Bind_parameterContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void enterBind_dynamic_sql(Bind_dynamic_sqlContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        return result;
    }

    public String replaceVariableStatements(final JQLContext jqlContext, final String jql, final JQLReplaceVariableStatementListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
