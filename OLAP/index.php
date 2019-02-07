    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <title>
        Data Warehousing and OLAP Bibliography
      </title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <script type="text/javascript" src="http://www.ondelette.com/jrefer.js"></script>

    </head>
    <body text="#000000" bgcolor="#ffffff" link="#0000ee" vlink="#990000" alink="#ff6633" style="width:600px;margin-left:auto;margin-right:auto;">
<?php
require_once("feedread.php");
?>
<p style="display:inline;float:right">
<?php
$feedOut= getSomeFeed("http://www.daniel-lemire.com/blog/archives/category/computer-science/data-warehousing-and-olap/atom", 10, true,"feed-lemiredwolapatom2",'',-1,-1,false,false);
if($feedOut) echo $feedOut;
?>
</p>
    <h1> Data Warehousing and OLAP  </h1>
    <h2> A Research-Oriented Bibliography  </h2>
    <p>A comprehensive list of Data Warehouse and OLAP papers (with link to full paper), links, and conferences.</p>
    <p><a href="#sendupdates">Please submit your papers and links!</a></p><p>Number of links: 206,  last updated on July  2005. </p>

<h2>Table of content</h2>
<ol style='list-style: upper-roman outside; margin-top:0.5em;margin-bottom:0.2em;'>
	<li style='font-weight:bold'>
		<a href="#1">Conferences, Journals, and Workshops</a>
		<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>
			<li><a href="#2_1" >Conferences</a> - <i>2 entries</i></li>
			<li><a href="#2_2" >Journals</a> - <i>1 entry</i></li>

			<li><a href="#2_3" >Workshops</a> - <i>3 entries</i></li>
		</ol>
	</li>
	<li style='font-weight:bold'>
		<a href="#2">Data Warehousing</a>
		<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>

			<li><a href="#3_1" >Data integration</a> - <i>5 entries</i></li>
			<li><a href="#3_2" >Design</a> - <i>9 entries</i></li>
			<li><a href="#3_3" >General</a> - <i>15 entries</i></li>

			<li><a href="#3_4" >Performance and Storage</a> - <i>3 entries</i></li>
			<li><a href="#3_5" >Projects</a> - <i>6 entries</i></li>
			<li><a href="#3_6" >Selecting Views to Materialize</a> - <i>9 entries</i></li>

			<li><a href="#3_7" >Text Mining</a> - <i>2 entries</i></li>
			<li><a href="#3_8" >Vendor Specific</a> - <i>1 entry</i></li>
			<li><a href="#3_9" >Warehouse Maintenance</a> - <i>33 entries</i></li>

		</ol>
	</li>
	<li style='font-weight:bold'>
		<a href="#3">OLAP</a>
		<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>
			<li><a href="#4_1" >Cube Maintenance</a> - <i>4 entries</i></li>

			<li><a href="#4_2" >Data Sets</a> - <i>2 entries</i></li>
			<li><a href="#4_3" >General</a> - <i>4 entries</i></li>
			<li><a href="#4_4" >Indexing</a> - <i>16 entries</i></li>

			<li><a href="#4_5" >Information Retrieval</a> - <i>2 entries</i></li>
			<li><a href="#4_6" >Location-Based Computing</a> - <i>1 entry</i></li>
			<li><a href="#4_7" >Modeling</a> - <i>6 entries</i></li>

			<li><a href="#4_8" >Open Source Software</a> - <i>1 entry</i></li>
			<li><a href="#4_9" >Query Languages</a> - <i>12 entries</i></li>
			<li><a href="#4_10" >Query evaluation - General</a> - <i>24 entries</i></li>

			<li><a href="#4_11" >Query evaluation - Iceberg</a> - <i>3 entries</i></li>
			<li><a href="#4_12" >Query evaluation - Parallel</a> - <i>2 entries</i></li>
			<li><a href="#4_13" >Query evaluation - Range Queries</a> - <i>5 entries</i></li>

			<li><a href="#4_14" >Spatio-Temporal Applications</a> - <i>6 entries</i></li>
			<li><a href="#4_15" >Storage and Chunks</a> - <i>9 entries</i></li>
			<li><a href="#4_16" >Vendor Specific</a> - <i>2 entries</i></li>

			<li><a href="#4_17" >Visualization</a> - <i>3 entries</i></li>
			<li><a href="#4_18" >White Papers</a> - <i>12 entries</i></li>
			<li><a href="#4_19" >XML</a> - <i>3 entries</i></li>

		</ol>
	</li>
</ol>

<h2>Content</h2>
<ol style='list-style: upper-roman outside; margin-top:0.5em;margin-bottom:0.2em;'>
	<li style='font-weight:bold'>
		<a name="1">Conferences, Journals, and Workshops</a>
		<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>
			<li><a name="2_1" style='background-color: #ffc; border:black solid 0.1em; '  >Conferences</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'><a href="http://www.vldb.org">VLDB: Very Large Data Base</a>.</li>
				<li style='text-align:justified'><a href="http://www.edbt.org">EDBT: Extending Database Technology</a>.</li>
			</ul>
</li>
			<li><a name="2_2" style='background-color: #ffc; border:black solid 0.1em; '  >Journals</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'><a href="http://www.idea-group.com/journals/details.asp?id=4291">International journal of data warehousing and mining</a>.</li>

			</ul>
</li>
			<li><a name="2_3" style='background-color: #ffc; border:black solid 0.1em; '  >Workshops</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'><a href="http://www.cis.drexel.edu/faculty/song/dolap.html">DOLAP: ACM International Workshop on Data Warehousing and OLAP</a>.</li>
				<li style='text-align:justified'><a href="https://www-927.ibm.com/ibm/cas/cascon/">CASCON: IBM Center for Advanced Studies Conference</a>.</li>
				<li style='text-align:justified'><a href="http://www.dexa.org">DAWAK: Data Warehousing and Knowledge Discovery</a>.</li>

			</ul>
</li>
		</ol>
	</li>
	<li style='font-weight:bold'>
		<a name="2">Data Warehousing</a>
		<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>
			<li><a name="3_1" style='background-color: #ffc; border:black solid 0.1em; '  >Data integration</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'>Michael Hahne, Lothar Burow,  and Torben Elvers, <a href="http://www.hahneonline.de/paper/DW2004_Hahne_paper.pdf">XML-Datenimport in das SAP Business Information Warehouse bei Bayer MaterialScience</a>,  Auf dem Weg zur Integration Factory, 231-251, 2005.</li>
				<li style='text-align:justified'>H. Fan and A. Poulovassilis, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p86-fan.pdf">Using AutoMed Metadata in Data Warehousing Environments</a>,  DOLAP, 2003.</li>
				<li style='text-align:justified'>Michael Hahne, <a href="http://www.hahneonline.de/paper/DW2002_Hahne_paper.pdf">Transformation mehrdimensionaler Datenmodelle</a>,  Vom Data Warehouse zum Corporate Knowledge Center, 399-420, 2002.</li>
				<li style='text-align:justified'>D. Calvanese, G. De Giacomo, M. Lenzerini, D. Nardi,  and R. Rosati, <a href="http://www.dblab.ntua.gr/%7Edwq/DWDOT-98_dwq.pdf">Source Integration in Data Warehousing</a>,  DEXA'98, 192--197, 1998.</li>

				<li style='text-align:justified'>G. Zhou, R. Hull, R. King,  and J.-C. Franchitti, <a href="ftp://ftp.research.microsoft.com/pub/debull/june95-letfinal.ps">Supporting Data Integration and Warehousing Using H2O</a>, IEEE Data Engineering Bulletin, <b>18</b>:2, 29-40, 1995.</li>
			</ul>
</li>
			<li><a name="3_2" style='background-color: #ffc; border:black solid 0.1em; '  >Design</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Michael Hahne, <a href="http://www.hahneonline.de/paper/BTW2003_Hahne_paper.pdf">Logische Datenmodellierung zur Abbildung mehrdimensionaler Datenstrukturen im SAP Business Information Warehouse</a>,  BTW, 630-647, 2003.</li>

				<li style='text-align:justified'>Michael Hahne, <a href="http://www.hahneonline.de/paper/CE03_Hahne_paper.pdf">Time aspects in SAP Business Information Warehouse</a>,  Concurrent Engineering - Enhanced Interoperable Systems, 69-74, 2003.</li>
				<li style='text-align:justified'>Michel Schneider, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/02_Schneider.pdf">Well-formed data warehouse structures</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>Michael Hahne, <a href="http://www.hahneonline.de/paper/MKWI2004_Hahne_paper.pdf">Grafische Reprsentation mehrdimensionaler Datenmodelle des SAP Business Information Warehouse</a>,  Multikonferenz Wirtschaftsinformatik (MKWI) 2004, 152-166, 2003.</li>
				<li style='text-align:justified'>Hajer Baazaoui Zghal, Sami Faiz,  and Henda Ben Ghezala, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/06_Zghal.pdf">CASME: A CASE Tool for Spatial Data Marts Design and Generation</a>,  DMDW, 2003.</li>

				<li style='text-align:justified'>Veronika Peralta and Raul Ruggia, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/03_Peralta.pdf">Using Design Guidelines to Improve Data Warehouse Logical Design</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>Sergio Lujan-Mora and Juan Trujillo, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/01_Lujan.ps">A Comprehensive Method for Data Warehouse Design</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>Andreas Bauer, Wolfgang Hmmer, Wolfgang Lehner,  and Lutz Schlesinger, <a href="http://www6.informatik.uni-erlangen.de/docs/HLBS02.pdf">A Decathlon in Multidimensional Modelling: Open Issues and Some Solutions</a>,  DaWaK, 274--285, 2002.</li>
				<li style='text-align:justified'>freedatawarehouse.com, <a href="http://freedatawarehouse.com/tutorials/dmtutorial/Dimensional%20Modeling%20Tutorial.aspx">Dimensional Modeling Tutorial</a>.</li>

			</ul>
</li>
			<li><a name="3_3" style='background-color: #ffc; border:black solid 0.1em; '  >General</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Rob Weir, Taoxin Peng,  and Jon Kerridge, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/05_Weir.pdf">Best Practice for Implementing a Data Warehouse: A Review for Strategic Alignment</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>J. Gray, H. Schek, M. Stonebraker,  and J. Ullman, <a href="http://research.microsoft.com/%7Egray/lowell/LowellDatabaseResearchSelfAssessment.pdf">Lowell Report</a>, 2003.</li>

				<li style='text-align:justified'>Stefano Rizzi, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/keynote.pdf">Open problems in data warehousing: eight years later</a>,  DMDW, 2003 (Keynote slides).</li>
				<li style='text-align:justified'>Eva Khn, <a href="http://www.vldb.org/conf/2003/papers/S33P02.pdf">The Zero-Delay Data Warehouse: Mobilizing Heterogeneous Databases</a>,  VLDB, 2003.</li>
				<li style='text-align:justified'>Surajit Chaudhuri and others, <a href="http://infolab.usc.edu/csci585/Spring2003/den_ar/chaudhuri_p48.pdf">Database technology for decision support systems</a>, IEEE Computer, 48--55, 2001.</li>
				<li style='text-align:justified'>Hector Garcia-Molina, Wilburt Juan Labio, Janet Wiener,  and Yue Zhuge, <a href="http://www-db.stanford.edu/%7Ewilburt/distrib.ps">Distributed and Parallel Computing Issues in Data Warehousing</a>,  PODC, 1998.</li>

				<li style='text-align:justified'>Surajit Chaudhuri and Umesh Dayal, <a href="http://www.acm.org/sigmod/record/issues/9703/chaudhuri.ps">An Overview of Data Warehousing and OLAP Technology</a>, ACM SIGMOD Record, <b>26</b>:1, 1997.</li>
				<li style='text-align:justified'>M.C. Wu and A.P. Buchmann, <a href="http://www.dvs1.informatik.tu-darmstadt.de/staff/wu/btwllncs.ps.gz">Research Issues in Data Warehousing</a>,  BTW, 1997.</li>
				<li style='text-align:justified'>Phillip M. Fernandez and Donovan Schneider, <a href="http://www-6.ibm.com/jp/software/data/developer/library/techdoc/pdf/sigmod96.pdf">The Ins and Outs (and everything in between) of Data Warehousing</a>,  ACM SIGMOD, 1996 (SIGMOD membership required).</li>

				<li style='text-align:justified'>Jennifer Widom, <a href="ftp://db.stanford.edu/pub/papers/warehouse-research.ps">Research Problems in Data Warehousing</a>,  Int'l Conf. on Information and Knowledge Management, 1995.</li>
				<li style='text-align:justified'>P. Raj, <a href="http://www.peterindia.net/CyberPage.html">Database Design Articles, Overviews, and Resources</a>.</li>
				<li style='text-align:justified'><a href="http://www.datawarehouse.org/">Data Warehousing Knowledge Center</a>.</li>
				<li style='text-align:justified'>Larry Greenfield, <a href="http://www.dwinfocenter.org/">Data Warehousing Information Center</a>.</li>

				<li style='text-align:justified'>R. Kimball, <a href="http://rkimball.com/html/articles.html">Various articles from Intelligent Enterprise magazine</a>.</li>
				<li style='text-align:justified'><a href="http://www.datawarehousingonline.com/">Data Warehousing Online</a>.</li>
			</ul>
</li>
			<li><a name="3_4" style='background-color: #ffc; border:black solid 0.1em; '  >Performance and Storage</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>M. Golfarelli and E. Saltarelli, <a href="">The Workload You Have, the Workload You Would Like</a>,  DOLAP, 2003.</li>

				<li style='text-align:justified'>Matthias Nicola and Haider Rizvi, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/07_Nicola.pdf">Storage Layout and I/O Performance in Data Warehouses</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>J. P. Costa and P. Furtado, <a href="http://portal.acm.org/ft_gateway.cfm?id=956068&type=pdf">DSQoS - Distributed Architecture Providing QoS in Summary Warehouses</a>,  DOLAP, 2003.</li>
			</ul>
</li>
			<li><a name="3_5" style='background-color: #ffc; border:black solid 0.1em; '  >Projects</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'>Tom Barclay, Jim Gray,  and Wyman Chong, <a href="ftp://ftp.research.microsoft.com/pub/tr/TR-2004-107.pdf">TerraServer Bricks -- A High Availability Cluster Alternative</a>, Microsoft Research, MSR-TR-2004-107, 2004.</li>
				<li style='text-align:justified'>Jovanka Adzic and Valter Fiore, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/08_Adzic.pdf">Data Warehouse Population Platform</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>H. Gupta and D. Srivastava, <a href="http://www-db.stanford.edu/%7Ehgupta/ps/dawn.ps">The Data Warehouse of Newsgroups</a>,  International Conference on Database Theory, 1999.</li>
				<li style='text-align:justified'>M. Jarke and Y. Vassiliou, <a href="http://www.dblab.ntua.gr/%7Edwq/iq97_dwq.pdf">Data Warehouse Quality Design: A Review of the DWQ Project</a>,  Information Quality, 1997.</li>

				<li style='text-align:justified'>N. Roussopoulos, C.M. Chen, S. Kelley, A. Delis,  and Y.Papakonstantinou, <a href="ftp://ftp.research.microsoft.com/pub/debull/june95-letfinal.ps">The Maryland ADMS Project: Views R Us</a>, IEEE Data Engineering Bulletin, <b>18</b>:2, 19--28, 1995.</li>
				<li style='text-align:justified'>J. Hammer, H. Garcia-Molina, W. Labio, J. Widom,  and Y. Zhuge, <a href="ftp://ftp.research.microsoft.com/pub/debull/june95-letfinal.ps">The Stanford Data Warehousing Project</a>, IEEE Data Engineering Bulletin, <b>18</b>:2, 41--48, 1995.</li>
			</ul>
</li>

			<li><a name="3_6" style='background-color: #ffc; border:black solid 0.1em; '  >Selecting Views to Materialize</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>D. Theodoratos and T. Sellis, <a href="http://www.dbnet.ece.ntua.gr/%7Edwq/p42.pdf">Designing Data Warehouses</a>, Data and Knowledge Engineering, <b>31</b>:3, 279--301, 1999.</li>
				<li style='text-align:justified'>H. Gupta and I.S. Mumick, <a href="http://www-db.stanford.edu/%7Ehgupta/ps/update.ps">Selection of Views to Materialize Under a Maintenance-Time Constraint</a>,  International Conference on Database Theory, 1999.</li>
				<li style='text-align:justified'>Yannis Kotidis and Nick Roussopoulos, <a href="http://www.cs.umd.edu/%7Enick/papers/sig99.ps">DynaMat: A Dynamic View Management System for Data Warehouses</a>,  ACM SIGMOD, 1999.</li>

				<li style='text-align:justified'>D. Theodoratos and T. Sellis, <a href="http://www.dbnet.ece.ntua.gr/%7Edwq/p44.pdf">Dynamic Data Warehouse Design</a>,  DaWaK, 1--10, 1999.</li>
				<li style='text-align:justified'>E. Baralis, S. Paraboschi,  and E. Teniente, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/BaralisPT97.html">Materialized view selection in a multidimensional database</a>,  VLDB, 156--165, 1997.</li>
				<li style='text-align:justified'>J. Yang, K. Karlapalem,  and Q. Li, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/YangKL97.html">Algorithms for materialized view design in data warehousing environment</a>,  VLDB, 136--145, 1997.</li>
				<li style='text-align:justified'>D. Theodoratos and T. Sellis, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/TheodoratosS97.html">Data warehouse configuration</a>,  VLDB, 126--135, 1997.</li>

				<li style='text-align:justified'>H. Gupta, <a href="http://www-db.stanford.edu/%7Ehgupta/ps/SelectionViews.ps">Selection of Views to Materialize in a Data Warehouse</a>,  Intl. Conf. on Database Theory, 1997.</li>
				<li style='text-align:justified'>V. Harinarayan, A. Rajaraman,  and J.D. Ullman, <a href="http://www-db.stanford.edu/pub/papers/cube.ps">Implementing Data Cubes Efficiently</a>,  ACM SIGMOD, 205--216, 1996.</li>
			</ul>
</li>
			<li><a name="3_7" style='background-color: #ffc; border:black solid 0.1em; '  >Text Mining</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'>Steven Keith, Owen Kaser,  and Daniel Lemire, <a href="http://www.daniel-lemire.com/fr/documents/publications/tr05-001.pdf">Analyzing Large Collections of Electronic Text Using OLAP</a>, UNBSJ CSAS, TR-05-001, 2005.</li>
				<li style='text-align:justified'>D. Sullivan, <a href="http://www.amazon.com/exec/obidos/tg/detail/-/0471399590/002-5524910-7515225?v=glance">Document Warehousing and Text Mining: Techniques for Improving Business Operations, Marketing, and Sales</a>, 2001.</li>
			</ul>
</li>
			<li><a name="3_8" style='background-color: #ffc; border:black solid 0.1em; '  >Vendor Specific</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'>Marc Bastien, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/09_Bastien.pdf">Accessing multidimensional Data Types in Oracle 9i Release 2</a>,  DMDW, 2003.</li>
			</ul>
</li>
			<li><a name="3_9" style='background-color: #ffc; border:black solid 0.1em; '  >Warehouse Maintenance</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Songting Chen and Elke A Rundensteiner, <a href="http://davis.wpi.edu/dsrg/icde05-gpivot.pdf">GPIVOT: Efficient Incremental Maintenance of Complex ROLAP Views</a>,  ICDE, 2005.</li>

				<li style='text-align:justified'>Songting Chen, Bin Liu,  and Elke A. Rundensteiner, <a href="http://davis.wpi.edu/dsrg/EVE/PAPERS/er-journal-2004.pdf">Multiversion Based View Maintenance Over Distributed Data Sources</a>,  ACM TODS, 2004.</li>
				<li style='text-align:justified'>Songting Chen, Jun Chen, Xin Zhang and Elke A. Rundensteiner, <a href="http://davis.wpi.edu/dsrg/dyda2.pdf">Detection and correction of conflicting source updates for view maintenance</a>,  ICDE, 2004.</li>
				<li style='text-align:justified'>Hao Fan and Alexandra Poulovassilis, <a href="http://www.dcs.bbk.ac.uk/%7Eap/pubs/dolap03.pdf">Using AutoMed metadata in data warehousing environments</a>,  DOLAP, 86--93, 2003.</li>
				<li style='text-align:justified'>H. Engstrom, S. Chakravarthy,  and B. Lings, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p71-engstrom.pdf">Maintenance Policy Selection in Heterogeneous Data Warehouse Environments: A Heuristics-based Approach</a>,  DOLAP, 2003.</li>

				<li style='text-align:justified'>Yannis Kotidis, <a href="http://www.research.att.com/%7Ekotidis/Publications/survey.pdf">Aggregate View Management in Data Warehouses in Handbook of Massive Data Sets</a>, 2002.</li>
				<li style='text-align:justified'>Yingwei Cui and Jennifer Widom, <a href="http://dbpubs.stanford.edu:8090/pub/2001-5">Lineage Tracing for General Data Warehouse Transformations</a>,  VLDB, 2001.</li>
				<li style='text-align:justified'>A. Gupta, I. S. Mumick, J. Rao,  and K. A. Ross, <a href="http://www.cs.columbia.edu/%7Ekar/pubsk/adaptation.ps">Adapting Materialized Views After Redefinitions: Techniques and a Performance Study</a>, Information Systems, 2001 (Special issue on Data Warehousing).</li>
				<li style='text-align:justified'>Y. Cui and J. Widom, <a href="http://www-db.stanford.edu/pub/papers/trace.ps">Practical Lineage Trac ing in Data Warehouses</a>,  ICDE, 2000.</li>

				<li style='text-align:justified'>J. Yang and J. Widom, <a href="http://www-db.stanford.edu/%7Ejunyang/papers/yw-tempsm.ps">Making Temporal Views Self-Maintainable for Data Warehousing</a>,  ICDE, 2000.</li>
				<li style='text-align:justified'>Wilburt Juan Labio, Ramana Yerneni,  and Hector Garcia-Molina, <a href="http://www-db.stanford.edu/%7Ewilburt/publications/vm.ps">Shrinking the Warehouse Update Window</a>,  ACM SIGMOD, 1999.</li>
				<li style='text-align:justified'>M. Bouzeghoub, F. Fabret,  and M. Matulovic, <a href="http://www.dbnet.ece.ntua.gr/%7Edwq/p32.pdf">Modeling Data Warehouse Refreshment Process as a Workflow Application</a>,  DMDW, 1999.</li>
				<li style='text-align:justified'>Daniel Barbar and Xintao Wu, <a href="http://dblp.uni-trier.de">The Role of Approximations in Maintaining and Using Aggregate Views</a>, IEEE Data Engineering Bulletin, <b>22</b>:4, 15-21, 1999.</li>

				<li style='text-align:justified'>P. Vassiliadis, M. Bouzeghoub,  and C. Quix, <a href="http://www.dbnet.ece.ntua.gr/%7Edwq/p41.pdf">Towards Quality-Oriented Data Warehouse Usage and Evolution</a>,  CAiSE, 1999 (best paper).</li>
				<li style='text-align:justified'>Carlos Hurtado, Alberto Mendelzon,  and Alejandro Vaisman, <a href="ftp://ftp.db.toronto.edu/pub/papers/HMVdolap99.ps.gz">Updating OLAP Dimensions</a>,  DOLAP, 60--66, 1999.</li>
				<li style='text-align:justified'>J. Yang and J. Widom, <a href="ftp://db.stanford.edu/pub/papers/yw-tempview.ps">Maintaining Temporal Views Over Non-Historical Information Sources For Data Warehousing</a>,  ICDE, 1998.</li>
				<li style='text-align:justified'>Y. Zhuge and H. Garcia-Molina, <a href="ftp://db.stanford.edu/pub/papers/zg-gsviews.ps">Graph Structured Views and Their Incremental Maintenance</a>,  ICDE, 1998.</li>

				<li style='text-align:justified'>H. Garcia-Molina, W. Labio,  and J. Yang, <a href="http://www-db.stanford.edu/pub/papers/vsmexpire.ps">Expiring Data in a Warehouse</a>,  VLDB, 1998.</li>
				<li style='text-align:justified'>H.V. Jagadish, P.P.S. Narayan, S. Seshadri, S. Sudarshan,  and R. Kanneganti, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/JagadishNSSK97.html">Incremental organization for data recording and warehousing</a>,  VLDB, 16--25, 1997.</li>
				<li style='text-align:justified'>Y. Zhuge, H. Garcia-Molina,  and J. L. Wiener, <a href="ftp://db.stanford.edu/pub/papers/dapd.ps">Consistency Algorithms for Multi-Source Warehouse View Maintenance</a>, Journal of Distributed and Parallel Databases, 1997.</li>
				<li style='text-align:justified'>Y. Zhuge, J. L. Wiener,  and H. Garcia-Molina, <a href="ftp://db.stanford.edu/pub/papers/mvc.ps">Multiple View Consistency for Data Warehousing</a>,  ICDE, 1997.</li>

				<li style='text-align:justified'>I.S. Mumick, D. Quass,  and B.S. Mumick, <a href="ftp://db.stanford.edu/pub/papers/cube-maint-sigmod.ps">Maintenance of data cubes and summary tables in a warehouse</a>,  ACM SIGMOD, 100-111, 1997.</li>
				<li style='text-align:justified'>D. Quass and J. Widom, <a href="ftp://db.stanford.edu/pub/papers/online.ps">On-Line Warehouse View Maintenance for Batch Updates</a>,  ACM SIGMOD, 393--404, 1997.</li>
				<li style='text-align:justified'>N. Huyn, <a href="http://www-db.stanford.edu/%7Ehuyn/papers/mvsm-vldb97.ps">Multiple-view self-maintenance in data warehousing environments</a>,  VLDB, 26--35, 1997.</li>
				<li style='text-align:justified'>P. Scheuermann, J. Shim,  and R. Vingralek, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/ScheuermannSV96.html">WATCHMAN: A Data Warehouse Intelligent Cache Manager</a>,  VLDB, 51--62, 1996.</li>

				<li style='text-align:justified'>G. Zhou, R. Hull,  and R. King, <a href="ftp://ftp.cs.colorado.edu/users/hull/squirrel:materialization-JIIS.ps">Generating Data Integration Mediators that Use Materialization</a>, Journal of Intelligent Information Systems, <b>3</b>:2, 99--221, 1996.</li>
				<li style='text-align:justified'>Y. Zhuge, H. Garcia-Molina,  and J. L. Wiener, <a href="ftp://db.stanford.edu/pub/papers/strobe.ps">The Strobe Algorithms for Multi-Source Warehouse Consistency</a>,  PDIS, 1996.</li>
				<li style='text-align:justified'>J.L. Wiener, H. Gupta, W.J. Labio, Y. Zhuge, H. Garcia-Molina,  and J. Widom, <a href="ftp://db.stanford.edu/pub/papers/whips-arch.ps">A System Prototype for Warehouse View Maintenance</a>,  ACM Workshop on Materialized Views: Techniques and Applications, 26--33, 1996.</li>

				<li style='text-align:justified'>W.J. Labio and H. Garcia-Molina, <a href="http://www-db.stanford.edu/pub/papers/window.ps">Efficient Snapshot Differential Algorithms for Data Warehousing</a>,  VLDB, 63--74, 1996.</li>
				<li style='text-align:justified'>D. Quass, A. Gupta, I.S. Mumick,  and J. Widom, <a href="ftp://db.stanford.edu/pub/papers/sm.ps">Making Views Self-Maintainable for Data Warehousing</a>,  PDIS, 1996.</li>
				<li style='text-align:justified'>N. Huyn, <a href="ftp://db.stanford.edu/pub/papers/cqvsm.ps">Efficient View Self-Maintenance</a>,  ACM Workshop on Materialized Views: Techniques and Applications, 1996.</li>
				<li style='text-align:justified'>M. Staudt and M. Jarke, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/StaudtJ96.html">Incremental Maintenance of Externally Materialized Views</a>,  VLDB, 75--86, 1996.</li>

				<li style='text-align:justified'>Y. Zhuge, H. Garcia-Molina, J. Hammer,  and J. Widom, <a href="ftp://db.stanford.edu/pub/papers/anomaly-full.ps">View Maintenance in a Warehousing Environment</a>,  ACM SIGMOD, 316--327, 1995.</li>
			</ul>
</li>
		</ol>
	</li>
	<li style='font-weight:bold'>
		<a name="3">OLAP</a>

		<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>
			<li><a name="4_1" style='background-color: #ffc; border:black solid 0.1em; '  >Cube Maintenance</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>M. Body, M. Miquel, Y. B�dard,  and A. Tchounikine, <a href="http://sirs.scg.ulaval.ca/yvanbedard/enseigne/SCG66124/316.pdf">Handling Evolutions in Multidimensional Structures</a>,  ICDE, 2003.</li>
				<li style='text-align:justified'>Heum-Geun Kang and Chin-Wan Chung, <a href="http://citeseer.ist.psu.edu/541932.html">Exploiting Versions for On-line Data Warehouse Maintenance in MOLAP Servers</a>,  VLDB, 2002.</li>
				<li style='text-align:justified'>Johann Eder and Christian Koncilia, <a href="http://citeseer.ist.psu.edu/461768.html">Evolution of Dimension Data in Temporal Data Warehouses</a>,  DaWaK, 284--293, 2001.</li>

				<li style='text-align:justified'>Carlos Hurtado, Alberto Mendelzon,  and Alejandro Vaisman, <a href="ftp://ftp.db.toronto.edu/pub/papers/HMVicde99.ps.gz">Maintaining Data Cubes under Dimension Updates</a>,  ICDE, 1999.</li>
			</ul>
</li>
			<li><a name="4_2" style='background-color: #ffc; border:black solid 0.1em; '  >Data Sets</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>C. Hahn, S. Warren,  and J. London, <a href="http://cdiac.ornl.gov/epubs/ndp/ndp026b/ndp026b.htm">Edited synoptic cloud reports from ships and land stations over the globe (1982-1991)</a>, 2001.</li>

				<li style='text-align:justified'>S. Hettich and S. D. Bay, <a href="http://kdd.ics.uci.edu">The UCI KDD archive</a>, University of California, Irvine, 2000.</li>
			</ul>
</li>
			<li><a name="4_3" style='background-color: #ffc; border:black solid 0.1em; '  >General</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Torben Bach Pedersen and Christian S. Jensen, <a href="http://infolab.usc.edu/csci599/Fall2002/paper/I1_pederson_p40.pdf">Multidimensional database technology</a>, IEEE Computer, 40--46, 2001.</li>

				<li style='text-align:justified'>A. Shoshani, <a href="http://www.lbl.gov/%7Earie/papers/olap.vs.sdb.paper.PODS97.ps">OLAP and Statistical Databases: Similarities and Differences</a>,  ACM PODS, 185--196, 1997.</li>
				<li style='text-align:justified'>Panos Vassiliadis and Timos Sellis, <a href="http://www.dbnet.ece.ntua.gr/%7Edwq/p31.pdf">A Survey on Logical Models for OLAP Databases</a>, DWQ-NTUA-301 (accepted for publication at SIGMOD RECORD).</li>
				<li style='text-align:justified'>D.J. Power, <a href="http://www.dssresources.com/researchers/index.html">Decision Support Systems Researcher Resources</a>.</li>
			</ul>

</li>
			<li><a name="4_4" style='background-color: #ffc; border:black solid 0.1em; '  >Indexing</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Ying Feng, Divykant Agrawal, Amr El Abbadi,  and Ahmed Metwally, <a href="http://www.cs.ucsb.edu/research/trcs/docs/2003-16.pdf">Range CUBE: Efficient Cube Computation by Exploiting Data Correlation</a>, Department of Computer Science, University of California, Santa Barbara, 16, 2003.</li>
				<li style='text-align:justified'>Y. Sismanis and A. Deligiannakis, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p17-sismanis.pdf">Hierarchical Dwarfs for the Rollup Cube</a>,  DOLAP, 2003.</li>
				<li style='text-align:justified'>Joseph M. Hellerstein, Elias Koutsoupias, Daniel P. Miranker, Christos H. Papadimitriou,  and Vasilis Samoladas, <a href="http://doi.acm.org/10.1145/505241.505244">On a model of indexability and its bounds for range queries</a>, J. ACM, <b>49</b>:1, 35--55, 2002 (ACM Membership required).</li>

				<li style='text-align:justified'>Yannis Sismanis, Antonios Deligiannakis, Nick Roussopoulus,  and Yannis Kotidis, <a href="http://www.cs.umd.edu/Library/TRs/CS-TR-4342/CS-TR-4342.pdf ">Dwarf: shrinking the petacube</a>,  ACM SIGMOD 2002, 464--475, 2002.</li>
				<li style='text-align:justified'>Thomas P. Nadeau and Toby J. Teorey, <a href="https://www-927.ibm.com/ibm/cas/archives/2001/papers/cascon01/pdf/nadeau.pdf">A Pareto Model for OLAP View Size Estimation</a>,  CASCON, 2001.</li>
				<li style='text-align:justified'>Daniel Barbar and Xintao Wu, <a href="http://citeseer.ist.psu.edu/235258.html">Using loglinear models to compress datacube</a>,  Web-Age Information Management, 311-322, 2000.</li>
				<li style='text-align:justified'>M.C. Wu and A.P. Buchmann, <a href="http://www.informatik.tu-darmstadt.de/DVS1/staff/wu/ieee_ebi.html">Encoded Bitmap Indexing for Data Warehouses</a>,  ICDE, 220-230, 1998.</li>

				<li style='text-align:justified'>Yannis Kotidis and Nick Roussopoulos, <a href="http://www.cs.umd.edu/%7Enick/papers/ROLAP-Views.html">An Alternative Storage Organization for ROLAP Aggregate Views Based on Cubetrees</a>,  ACM SIGMOD, 249--258, 1998.</li>
				<li style='text-align:justified'>C.Y. Chan and Y.E. Ioannidis, <a href="http://www.cs.wisc.edu/%7Ecychan/101.ps">Bitmap Index Design and Evaluation</a>,  ACM SIGMOD, 355--366, 1998.</li>
				<li style='text-align:justified'>W. Labio, D. Quass,  and B. Adelberg, <a href="ftp://db.stanford.edu/pub/papers/view.ps">Physical Database Design for Data Warehousing</a>,  ICDE, 1997.</li>
				<li style='text-align:justified'>P. O'Neil and D. Quass, <a href="http://www.cs.umb.edu/%7Eponeil/varindexx.ps">Improved query performance with variant indexes</a>,  ACM SIGMOD, 38--49, 1997.</li>

				<li style='text-align:justified'>T. Johnson and D. Shasha, <a href="ftp://ftp.research.microsoft.com/pub/debull/mar97-letfinal.ps">Some Approaches to Index Design for Cube Forests</a>, IEEE Data Engineering Bulletin, <b>20</b>:1, 27--35, 1997.</li>
				<li style='text-align:justified'>H. Lei and K. A. Ross, <a href="http://www.cs.columbia.edu/%7Ekar/pubsk/stripedke.ps">Faster Joins, Self-Joins and Multi-Way Joins Using Join Indices</a>,  Next Generation Information Technologies and Systems, 1997 (Extended version to appear in Data and Knowledge Engineering).</li>
				<li style='text-align:justified'>H. Gupta, V. Harinarayan, A. Rajaraman,  and J. Ullman, <a href="ftp://db.stanford.edu/pub/papers/CubeIndex.ps">Index Selection for OLAP</a>,  ICDE, 1997.</li>

				<li style='text-align:justified'>S. Sarawagi, <a href="ftp://ftp.research.microsoft.com/pub/debull/mar97-letfinal.ps">Indexing OLAP Data</a>, IEEE Data Engineering Bulletin, <b>20</b>:1, 36--43, 1997.</li>
				<li style='text-align:justified'>Informix Corporation, <a href="http://www.informix.com/informix/corpinfo/zines/whitpprs/wpxps.pdf">INFORMIX-OnLine Extended Parallel Server and INFORMIX-Universal Server: A New Generation of Decision-Support Indexing for Enterprise Data Warehouses</a>.</li>
			</ul>
</li>
			<li><a name="4_5" style='background-color: #ffc; border:black solid 0.1em; '  >Information Retrieval</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'>Josiane Mothe, Claude Chrisment, Bernard Dousset,  and Joel Alaux, <a href="http://www.irit.fr/recherches/IRI/SIG/personnes/mothe/pub/JASIST03.pdf">DocCube: multi-dimensional visualization and exploration of large document sets</a>, Journal of the American Society for Information Science and Technology, <b>54</b>:7, 650--659, 2003.</li>
				<li style='text-align:justified'>M. C. McCabe, J. Lee, A. Chowdhury, D. Grossman,  and O. Frieder, <a href="http://www.ir.iit.edu/publications/downloads/sigir2k.pdf">On the design and evaluation of a multi-dimensional approach to information retrieval</a>,  SIGIR '00, 363--365, 2000.</li>
			</ul>
</li>
			<li><a name="4_6" style='background-color: #ffc; border:black solid 0.1em; '  >Location-Based Computing</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'>Christian S. Jensen, Augustas Kligys, Torben Bach Pedersen,  and Igor Timko, <a href="http://portal.acm.org/citation.cfm?id=962153&jmp=cit&coll=portal&dl=ACM&CFID=16682089&CFTOKEN=35550861#">Multidimensional data modeling for location-based services</a>, The VLDB Journal, <b>13</b>:1, 1--21, 2004 (ACM Membership required.).</li>
			</ul>
</li>
			<li><a name="4_7" style='background-color: #ffc; border:black solid 0.1em; '  >Modeling</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Carlos Hurtado and Alberto Mendelzon, <a href="http://www.cs.toronto.edu/%7Echl/papers/icdt01.ps">Reasoning about Summarizability in Heterogeneous Multidimensional Schemas</a>,  ICDT, 2001.</li>

				<li style='text-align:justified'>Tapio Niemi, Jyrki Nummenmaa,  and Peter Thanisch, <a href="http://link.springer.de/link/service/series/0558/bibs/1874/18740199.htm">Functional Dependencies in Controlling Sparsity of OLAP Cubes</a>,  DaWaK, 2000.</li>
				<li style='text-align:justified'>Andreas Bauer, Wolfgang Hmmer,  and Wolfgang Lehner, <a href="http://link.springer.de/link/service/series/0558/bibs/1874/18740189.htm">An Alternative Relational OLAP Modeling Approach</a>,  DAWAK, 2000.</li>
				<li style='text-align:justified'>Matteo Golfarelli, Dario Maio,  and Stefano Rizzi, <a href="http://link.springer.de/link/service/series/0558/bibs/1874/18740011.htm">Applying Vertical Fragmentation Techniques in Logical Design of Multidimensional Databases</a>,  DaWaK, 2000.</li>
				<li style='text-align:justified'>M. Gyssens and L.V.S. Lakshmanan, <a href="ftp://ftp.cs.concordia.ca/pub/laks/papers/vldb97.ps.gz">A foundation for multi-dimensional databases</a>,  VLDB, 106--115, 1997.</li>

				<li style='text-align:justified'>R. Agrawal, A. Gupta,  and S. Sarawagi, <a href="http://www.almaden.ibm.com/cs/people/ragrawal/papers/md_model_rj.ps">Modeling Multidimensional Databases</a>,  ICDE, 1997.</li>
			</ul>
</li>
			<li><a name="4_8" style='background-color: #ffc; border:black solid 0.1em; '  >Open Source Software</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Julian Hyde et al., <a href="http://apoptosis.dyndns.org:8080/open/mondrian/doc/index.html">Mondrian OLAP Server, a java-based ROLAP engine running over existing relational databases</a>.</li>

			</ul>
</li>
			<li><a name="4_9" style='background-color: #ffc; border:black solid 0.1em; '  >Query Languages</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>A. Abello, J. Samos,  and F. Saltor, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p56-abello.pdf">Implementing Operations to Navigate Semantic Star Schemas</a>,  DOLAP, 2003.</li>
				<li style='text-align:justified'>Alejandro Vaisman and Alberto Mendelzon, <a href="http://www.cs.toronto.edu/DB/WebPage/files/ATemporalQueryLanguageForOLAP.pdf">A Temporal Query Language for OLAP: Implementation and a Case Study</a>,  DBPL, 2001.</li>

				<li style='text-align:justified'>Alberto Mendelzon and Alejandro Vaisman, <a href="ftp://ftp.db.toronto.edu/pub/papers/vldb00.pdf">Temporal Queries in OLAP</a>,  VLDB, 2000.</li>
				<li style='text-align:justified'>P. Marcel, <a href="http://www.blois.univ-tours.fr/%7Emarcel/nisj.ps">Modeling and querying multidimensional databases: An overview.</a>, Networking and Information Systems Journal, <b>2</b>:5, 515--548, 1999.</li>
				<li style='text-align:justified'>J.-F. Boulicaut, P. Marcel,  and C. Rigotti, <a href="http://www.blois.univ-tours.fr/%7Emarcel/DOLAP99.ps">Query driven knowledge discovery in multidimensional data</a>,  DOLAP, 87--93, 1999.</li>

				<li style='text-align:justified'>J.-F. Boulicaut, P. Marcel, F. Pinet,  and C. Rigotti, <a href="http://www.blois.univ-tours.fr/%7Emarcel/DDLP98.ps">Spreadsheet form generation from rule-based specifications</a>,  DDLP, 59--70, 1998.</li>
				<li style='text-align:justified'>M. S. Hacid, P. Marcel,  and C. Rigotti, <a href="http://www.blois.univ-tours.fr/%7Emarcel/CDB97.ps">A Rule Based CQL for 2-Dimensional Tables</a>,  Constraint Database Systems, 92--104, 1997.</li>
				<li style='text-align:justified'>M. S. Hacid, P. Marcel,  and C. Rigotti, <a href="http://www.blois.univ-tours.fr/%7Emarcel/DDLP97.ps">A Rule-Based Language for Ordered Multidimensional Databases</a>,  Deductive Databases and Logic Programming, 69--81, 1997.</li>
				<li style='text-align:justified'>J. Gray, A. Bosworth, A. Layman,  and H. Pirahesh, <a href="http://paul.rutgers.edu/%7Eaminabdu/cs541/cube_op.pdf">Data cube: a relational aggregation operator generalizing group-by, cross-tabs and subtotals</a>, Data Mining and Knowledge Discovery, 1, 29--53, 1997.</li>

				<li style='text-align:justified'>M. S. Hacid, P. Marcel,  and C. Rigotti, <a href="http://www.blois.univ-tours.fr/%7Emarcel/DOOD97.ps">A rule based data manipulation language for OLAP systems</a>,  DOOD, 1997.</li>
				<li style='text-align:justified'>Leonid Libkin, Rona Machlin,  and Limsoon Wong, <a href="http://www.acm.org/pubs/citations/proceedings/mod/233269/p228-libkin/">A Query Language for Multidimensional Arrays: Design, Implementation, and Optimization Techniques</a>,  ACM SIGMOD, 228--239., 1996.</li>
				<li style='text-align:justified'>D. Chatziantoniou and K.A. Ross, <a href="http://www.informatik.uni-trier.de/%7Eley/vldb/ChatziantoniouR96/Article.PS">Querying Multiple Features of Groups in Relational Database</a>,  VLDB, 1996.</li>
			</ul>

</li>
			<li><a name="4_10" style='background-color: #ffc; border:black solid 0.1em; '  >Query evaluation - General</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>D. Theodoratos, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p63-theodoratos.pdf">Exploiting Hierarchical Clustering in Evaluating Multidimensional Aggregation Queries</a>,  DOLAP, 2003.</li>
				<li style='text-align:justified'>C.-S. Park, M. H. Kim,  and Y.-J. Lee, <a href="http://dbserver.kaist.ac.kr/%7Eparkcs/parkcs-rewriting-ICDE2001.ps">Rewriting OLAP Queries Using Materialized Views and Dimension Hierarchies in Data Warehouses</a>,  ICDE, 2001.</li>
				<li style='text-align:justified'>C.-S. Park, M. H. Kim,  and Y.-J. Lee, <a href="http://dbserver.kaist.ac.kr/%7Eparkcs/PKL01b.ps">Finding an Efficient Rewriting of OLAP Queries Using Materialized Views in Data Warehouses</a>, Decision Support Systems, 2001.</li>

				<li style='text-align:justified'>K. A. Ross and K. A. Zaman, <a href="http://www.cs.columbia.edu/%7Ekar/pubsk/selcube.ps">Optimizing Selections over Datacubes</a>,  SSDBM, 2000.</li>
				<li style='text-align:justified'>A. Balmin, Y. Papakonstantinou,  and T. Papadimitriou, <a href="http://www.db.ucsd.edu/publications/sesame.pdf">Hypothetical Queries in an OLAP Environment</a>,  VLDB, 2000.</li>
				<li style='text-align:justified'>K. A. Ross and K. A. Zaman, <a href="http://www.cs.columbia.edu/%7Ekar/pubsk/memcube.ps">Serving Datacube Tuples from Main Memory</a>,  SSDBM, 2000.</li>
				<li style='text-align:justified'>Mirek Riedewald, Divyakant Agrawal,  and Amr El Abbadi, <a href="http://citeseer.ist.psu.edu/article/riedewald00pcube.html">pCube: update-efficient online aggregation with progressive feedback and error bounds</a>,  SSDM, 95-108, 2000.</li>

				<li style='text-align:justified'>H. V. Jagadish, Laks V. S. Lakshmanan,  and Divesh Srivastava, <a href="http://www.research.att.com/%7Edivesh/papers/jls99-snakes.ps">Snakes and sandwiches: Optimal clustering strategies for a data warehouse</a>,  ACM SIGMOD, 1999.</li>
				<li style='text-align:justified'>John R. Smith, Chung-Sheng Li, Vittorio Castelli,  and Anant Jhingran, <a href="http://www.acm.org/pubs/citations/proceedings/pods/275487/p274-smith/">Dynamic Assembly of Views in Data Cubes</a>,  ACM PODS, 274--283, 1998.</li>
				<li style='text-align:justified'>Yihong Zhao, Karthikeyan Ramasamy, Kristin Tufte,  and Jeffrey F. Naughton, <a href="http://www.cs.wisc.edu/%7Eramasamy/paper/array.ps">Array-Based Evaluation of Multi-Dimensional Queries in Object Relational Database Systems</a>,  ICDE, 1998.</li>
				<li style='text-align:justified'>K. A. Ross, D. Srivastava,  and D. Chatziantoniou, <a href="http://www.cs.columbia.edu/%7Ekar/pubsk/mfcubeedbt.ps">Complex Aggregation at Multiple Granularities</a>,  EDBT, 1998.</li>

				<li style='text-align:justified'>Yihong Zhao, Prasad M.Deshpande, Jeffrey F. Naughton,  and Amit Shukla, <a href="http://www.cs.wisc.edu/%7Ezhao/sigmod98.ps">Simultaneous Optimization and Evaluation of Multiple Dimensional Queries</a>,  ACM SIGMOD, 271--282, 1998.</li>
				<li style='text-align:justified'>Y. Zhao, P.M. Deshpande,  and J.F. Naughton, <a href="http://www.cs.wisc.edu/%7Ezhao/cube2.ps">An Array-Based Algorithm for Simultaneous Multidimensional Aggregates</a>,  ACM SIGMOD, 159--170, 1997.</li>
				<li style='text-align:justified'>C. Dyreson, <a href="ftp://ftp.research.microsoft.com/pub/debull/mar97-letfinal.ps">Using an Incomplete Data Cube as a Summary Data Sieve</a>, IEEE Data Engineering Bulletin, <b>20</b>:1, 19--26, 1997.</li>

				<li style='text-align:justified'>V. Harinarayan, <a href="ftp://ftp.research.microsoft.com/pub/debull/mar97-letfinal.ps">Issues in Interactive Aggregation</a>, IEEE Data Engineering Bulletin, <b>20</b>:1, 12--18, 1997.</li>
				<li style='text-align:justified'>C.-T. Ho, J. Bruck,  and R. Agrawal, <a href="http://www.acm.org/pubs/citations/proceedings/pods/263661/p228-ho/">Partial-Sum Queries in OLAP Data Cubes Using Covering Codes</a>,  ACM PODS, 228--237, 1997.</li>
				<li style='text-align:justified'>P.M. Deshpande, J.F. Naughton, K. Ramasamy, A. Shukla, K. Tufte,  and Y. Zhao, <a href="ftp://ftp.research.microsoft.com/pub/debull/mar97-letfinal.ps">Cubing Algorithms, Storage Estimation, and Storage and Processing Alternatives for OLAP</a>, IEEE Data Engineering Bulletin, <b>20</b>:1, 3--11, 1997.</li>

				<li style='text-align:justified'>N. Roussopoulos, Y. Kotidis,  and M. Roussopoulos, <a href="http://www.cs.umd.edu/%7Enick/projects/Abstract.html">Cubetree: organization of and bulk incremental updates on the data cube</a>,  ACM SIGMOD, 89--99, 1997 (abstract and demo).</li>
				<li style='text-align:justified'>S.G. Rao, A. Badia,  and D. Van Gucht, <a href="ftp://ftp.cs.indiana.edu/pub/techreports/TR452.ps.Z">Providing Better Support for a Class of Decision Support Queries</a>,  ACM SIGMOD, 217--227, 1996.</li>
				<li style='text-align:justified'>A. Shukla, P.M. Deshpande, J.F. Naughton,  and K. Ramasamy, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/ShuklaDNR96.html">Storage Estimation for Multidimensional Aggregates in the Presence of Hierarchies</a>,  VLDB, 522--531, 1996.</li>
				<li style='text-align:justified'>C. Dyreson, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/Dyreson96.html">Information Retrieval from an Incomplete Data Cube</a>,  VLDB, 532--543, 1996.</li>

				<li style='text-align:justified'>S. Agarwal, R. Agrawal, P.M. Deshpande, A. Gupta, J.F. Naughton, R. Ramakrishnan,  and S. Sarawagi, <a href="http://www.informatik.uni-trier.de/%7Eley/db/conf/vldb/AgarwalADGNRS96.html">On the Computation of Multidimensional Aggregates</a>,  VLDB, 506--521, 1996.</li>
				<li style='text-align:justified'>S. Sarawagi, R. Agrawal,  and A. Gupta, <a href="http://www.almaden.ibm.com/cs/people/ragrawal/papers/cube_rj.ps">On Computing the Data Cube</a>, IBM Almaden Research Center, 10026, 1996.</li>
				<li style='text-align:justified'>A. Gupta, V. Harinarayan,  and D. Quass, <a href="http://www-db.stanford.edu/pub/papers/vldb.ps">Aggregate query processing in data warehousing environments</a>,  VLDB, 358--369, 1995.</li>
			</ul>

</li>
			<li><a name="4_11" style='background-color: #ffc; border:black solid 0.1em; '  >Query evaluation - Iceberg</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Jian Pei, Moonjung Cho,  and David Cheung, <a href="http://www.cs.sfu.ca/ jpei/publications/ctc-sdm05.pdf">Cross Table Cubing: Mining Iceberg Cubes from Data Warehouses</a>,  SDM'05, 2005.</li>
				<li style='text-align:justified'>Dong Xin, Jiawei Han, Xiaolei Li,  and Benjamin W. Wah, <a href="http://www.vldb.org/conf/2003/papers/S15P02.pdf">Star-Cubing: Computing Iceberg Cubes by Top-Down and Bottom-Up Integration</a>,  VLDB, 2003.</li>
				<li style='text-align:justified'>M. Fang, N. Shivakumar, H. Garcia-Molina, R. Motwani,  and J. Ullman, <a href="http://www-db.stanford.edu/pub/papers/iceberg-vldb98.ps">Computing iceberg queries efficiently</a>,  VLDB, 1998.</li>

			</ul>
</li>
			<li><a name="4_12" style='background-color: #ffc; border:black solid 0.1em; '  >Query evaluation - Parallel</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Frank Dehne, Todd Eavis,  and Andrew Rau-Chaplin, <a href="http://www.cs.dal.ca/%7Earc/publications/2-26/paper.pdf">Coarse grained parallel on-line analytical processing (OLAP) for data mining</a>,  ICCS, 2001.</li>
				<li style='text-align:justified'>Sanjay Goil, <a href="http://citeseer.ist.psu.edu/goil98high.html">High performance on-line analytical processing and data mining on parallel computers</a>, 1999.</li>

			</ul>
</li>
			<li><a name="4_13" style='background-color: #ffc; border:black solid 0.1em; '  >Query evaluation - Range Queries</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Cyrus Shahabi, Mehrdad Jahangiri,  and Dimitris Sacharidis, <a href="http://www.igi-online.com/downloads/pdf/ITJ2783_h8AQIgOg5Y.pdf">Hybrid Query and Data Ordering for Fast and Progressive Range-Aggregate Query Answering</a>, International Journal of Data Warehousing and Mining, 2005.</li>
				<li style='text-align:justified'>C.K. Poon, <a href="http://www.cs.cityu.edu.hk/%7Eckpoon/research/olap2.ps">Optimal Range Max Datacube for Fixed Dimensions</a>,  ICDT, 158--172, 2003.</li>

				<li style='text-align:justified'>C.K. Poon, <a href="http://www.cs.cityu.edu.hk/%7Eckpoon/research/olap1full.ps">Dynamic Orthogonal Range Queries in OLAP</a>, Theoretical Computer Science, <b>296</b>:3, 487--510, 2003.</li>
				<li style='text-align:justified'>Daniel Lemire, <a href="http://www.daniel-lemire.com/fr/documents/publications/multihaar_final.pdf">Wavelet-Based Relative Prefix Sum Methods for Range Sum Queries in Data Cubes</a>,  CASCON, 2002.</li>
				<li style='text-align:justified'>Jeffrey Scott Vitter and Min Wang, <a href="http://citeseer.ist.psu.edu/vitter99approximate.html">Approximate computation of multidimensional aggregates of sparse data using wavelets</a>,  ACM SIGMOD, 193--204, 1999.</li>

			</ul>
</li>
			<li><a name="4_14" style='background-color: #ffc; border:black solid 0.1em; '  >Spatio-Temporal Applications</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>P. Marchand, A. Brisebois, Y. B�dard,  and G. Edwards, <a href="http://sirs.scg.ulaval.ca/yvanbedard/enseigne/SCG66124/345-A.pdf">Implementation and evaluation of a hypercube-based method for spatio-temporal exploration and analysis</a>, Journal of the International Society of Photogrammetry and Remote Sensing (ISPRS), 2004 (to appear).</li>
				<li style='text-align:justified'>B. Wang, F. Pan, D. Ren, Y. Cui, D. Ding,  and W. Perrizo, <a href="http://www.cs.ndsu.nodak.edu/ datasurg/papers/review/03_dmkd_OLAP.pdf">Efficient OLAP Operations for Spatial Data Using Peano Trees</a>,  8th ACM SIGMOD Workshop on Research Issues in Data Mining and Knowledge Discovery, 2003.</li>

				<li style='text-align:justified'>Y. B�dard, P. Gosselin, S. Rivest, M.J. Proulx, M. Nadeau, G. Lebel,  and M.F. Gagnon, <a href="http://www.cin.ufpe.br/%7Erdnf/solap/Integrating GIS Components with Knowledge Discovery Technology for Environmental Health Decision Support.pdf">Integrating GIS Components with Knowledge Discovery Technology for Environmental Health Decision Support</a>, International Journal of Medical Informatics, <b>70</b>:1, 79--94,  2003.</li>
				<li style='text-align:justified'>F. Rao, L. Zhang, X. L. Yu, Y. Li,  and Y. Chen, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p48-rao.pdf">Spatial Hierarchy and OLAP-Favored Search in Spatial Data Warehouse</a>,  DOLAP, 2003.</li>
				<li style='text-align:justified'>Y. B�dard, T. Merrett,  and J. Han, <a href="http://www.cin.ufpe.br/%7Erdnf/solap/fundamentals of SDW.pdf">Fundamentals of Spatial Data Warehousing for Geographic Knowledge Discovery in Geographic Data Mining and Knowledge Discovery</a>, 53--73, 2001 (Research Monographs in GIS series edited by Peter Fisher and Jonathan Raper).</li>

				<li style='text-align:justified'>S. Rivest, Y. B�dard,  and P. Marchand, <a href="http://sirs.scg.ulaval.ca/yvanbedard/enseigne/SCG66124/273_SOLAP.pdf">Towards better support for spatial decision-making: Defining the characteristics of Spatial On-Line Analytical Processing (SOLAP)</a>, Geomatica, the journal of the Canadian Institute of Geomatics, <b>55</b>:2001, 539--555, 2001.</li>
			</ul>
</li>
			<li><a name="4_15" style='background-color: #ffc; border:black solid 0.1em; '  >Storage and Chunks</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>O. Kaser and D. Lemire, <a href="http://www.daniel-lemire.com/fr/documents/publications/p19-kaser-nrc.pdf">Attribute Value Reordering for Efficient Hybrid OLAP</a>,  DOLAP, 2003.</li>

				<li style='text-align:justified'>Owen Kaser, <a href="http://citeseer.ist.psu.edu/kaser02compressing.html">Compressing MOLAP arrays by attribute-value reordering: an experimental analysis</a>, University of New Brunswick (Saint John), CSAS, TR-02-001, 2002.</li>
				<li style='text-align:justified'>David Wai-Lok Cheung, Bo Zhou, Ben Kao, Hu Kan,  and Sau Dan Lee, <a href="http://citeseer.ist.psu.edu/361334.html">Towards the building of a dense-region-based OLAP system</a>, Data and Knowledge Engineering, <b>36</b>:1, 1-27, 2001.</li>
				<li style='text-align:justified'>Prasad Deshpande and Jeffrey F. Naughton, <a href="http://www.cs.wisc.edu/%7Epmd/papers/edbt2000final.pdf">Aggregate Aware Caching for Multi-Dimensional Queries</a>,  EDBT, 2000.</li>

				<li style='text-align:justified'>David Wai-Lok Cheung, Bo Zhou, Ben Kao, Kan Hu,  and Sau Dan Lee, <a href="http://citeseer.ist.psu.edu/cheung99drolap.html">DROLAP - a dense-region based approach to on-line analytical processing</a>,  Database and Expert Systems Applications, 761-770, 1999.</li>
				<li style='text-align:justified'>Prasad Deshpande, Karthikeyan Ramasamy, Amit Shukla,  and Jeffrey F. Naughton, <a href="http://www.cs.wisc.edu/%7Eramasamy/paper/cache.ps">Caching Multidimensional Queries Using Chunks</a>,  ACM SIGMOD, 259--270, 1998.</li>
				<li style='text-align:justified'>Yihong Zhao, Prasad M. Deshpande,  and Jeffrey F. Naughton, <a href="http://doi.acm.org/10.1145/253260.253288">An array-based algorithm for simultaneous multidimensional aggregates</a>,  Proceedings of the 1997 ACM SIGMOD International Conference on Management of Data, 159--170, 1997.</li>
				<li style='text-align:justified'>Wee-Keong Ng and Chinya V. Ravishankar, <a href="http://www.cais.ntu.edu.sg:8000/ wkn/paper/block.ps.gz">Block-oriented compression techniques for large statistical databases</a>, IEEE KDE, <b>9</b>:2, 314--328, 1997.</li>

				<li style='text-align:justified'>Sunita Sarawagi and Michael Stonebraker, <a href="http://citeseer.ist.psu.edu/sarawagi94efficient.html">Efficient organization of large multidimensional arrays</a>,  ICDE, 1994.</li>
			</ul>
</li>
			<li><a name="4_16" style='background-color: #ffc; border:black solid 0.1em; '  >Vendor Specific</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Mosha Pasumansky, <a href="http://www.mosha.com/msolap/">Microsoft OLAP and Analysis Services</a>.</li>

				<li style='text-align:justified'>freedatawarehouse.com, <a href="http://freedatawarehouse.com/tutorials/msatutorial/Microsoft%20Analysis%20Services%20Tutorial.aspx">Microsoft Analysis Services Tutorial</a>.</li>
			</ul>
</li>
			<li><a name="4_17" style='background-color: #ffc; border:black solid 0.1em; '  >Visualization</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>Andreas S. Maniatis, Panos Vassiliadis, Spiros Skiadopoulos,  and Yannis Vassiliou, <a href="http://www.dbnet.ece.ntua.gr/ andreas/publications/MVSV_DOLAP03_CR.pdf">Advanced visualization for OLAP</a>,  DOLAP, 9--16, 2003.</li>

				<li style='text-align:justified'>A. S. Maniatis, P. Vassiliadis, S. Skiadopoulos,  and Y. Vassiliou, <a href="http://www.cs.brown.edu/courses/cs227/Papers/Visualization/DOLAPmaniatis.pdf">Advanced Visualization for OLAP</a>,  DOLAP, 2003.</li>
				<li style='text-align:justified'>Y.-W. Choong, D. Laurent,  and P. Marcel, <a href="http://www.cis.drexel.edu/faculty/song/Dolap/paper/paper01/Choong - 3.pdf">Computing Appropriate Representations for Multidimensional Data</a>,  DOLAP, 2001.</li>
			</ul>
</li>
			<li><a name="4_18" style='background-color: #ffc; border:black solid 0.1em; '  >White Papers</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>

				<li style='text-align:justified'><a href="http://www.microstrategy.com/publications/WhitePapers/Index.htm">MicroStrategy</a>.</li>
				<li style='text-align:justified'><a href="http://www.comshare.com/library/mpcwhitepaper.pdf">Comshare</a>.</li>
				<li style='text-align:justified'><a href="http://www.informix.com/informix/solutions/dw/Data_War.pdf">Red Brick/Informix</a>.</li>
				<li style='text-align:justified'><a href="http://www.pilotsoftware.com/whitepaperlist.html">Pilot Software</a>.</li>
				<li style='text-align:justified'><a href="http://www.businessobjects.com/products/">Business Objects</a>.</li>

				<li style='text-align:justified'><a href="http://www.contourcomponents.com/white_papers.htm">Contour Components Limited</a>.</li>
				<li style='text-align:justified'><a href="http://support.cognos.com/consulting/cognos_approach/index.html">Cognos</a>.</li>
				<li style='text-align:justified'><a href="http://technet.oracle.com/">Oracle</a>.</li>
				<li style='text-align:justified'><a href="http://www.olapcouncil.org/research/whtpaply.htm">OLAP Council</a>.</li>
				<li style='text-align:justified'><a href="http://www.software.ibm.com/data/pubs/papers/">IBM</a>.</li>

				<li style='text-align:justified'><a href="http://www.microsoft.com/data/oledb/olap/">Microsoft OLE DB for OLAP</a>.</li>
				<li style='text-align:justified'><a href="http://www.disc.com/whtpaper.html">DISC's OMNIDEX</a>.</li>
			</ul>
</li>
			<li><a name="4_19" style='background-color: #ffc; border:black solid 0.1em; '  >XML</a>			<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>
				<li style='text-align:justified'>W. Hmmer, A. Bauer,  and G. Harde, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p33-huemmer.pdf">XCube -- XML For Data Warehouses</a>,  DOLAP, 2003.</li>

				<li style='text-align:justified'>Tapio Niemi, Marko Niinimaki, Jyrki Nummenmaa,  and Peter Thanisch, <a href="http://sunsite.informatik.rwth-aachen.de/Publications/CEUR-WS//Vol-77/04_Niemi.ps">Applying Grid Technologies to XML Based OLAP Cube Construction</a>,  DMDW, 2003.</li>
				<li style='text-align:justified'>D. Pedersen and T. B. Pedersen, <a href="http://www.cis.drexel.edu/faculty/song/dolap03/paper/p25-pedersen.pdf">Achieving Adaptivity for OLAP-XML Federations</a>,  DOLAP, 2003.</li>
			</ul>
</li>
		</ol>
	</li>

</ol>

    <hr />
        <h2> About this bibliography </h2>
        <p>
          This bibliography was previously maintained by <a
          href="http://www.cs.toronto.edu/%7Emendel">Alberto
          Mendelzon</a> up until 1997, then by <a
          href="http://www.dcc.uchile.cl/~churtado/">Carlos
          Hurtado</a> up until 2001.
          It is currently maintained by <a
          href="http://www.daniel-lemire.com/en/">Daniel Lemire</a>

          (professor, Universit� du Qu�bec  Montr�al, UQM).</p>
        <h2>Can I get the BibTeX file?</h2>
        <p>
          You can download the <a href="dw.bib">bibtex file here</a>.</p>
        <br />
        <h2> <a name="sendupdates">Help us keep this bibliography current and complete! </h2>

 
        <p>Please send comments or updates to 
          <a href="mailto:olapbibATondeletteDOTcom">olapbibATondeletteDOTcom</a>.
          We try to link mostly to freely available resources. New conference
          or journal papers are especially welcome.</a></p>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />

        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />

        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />

        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
      </body>
    </html>