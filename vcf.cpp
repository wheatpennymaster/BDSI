#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct vcf
{
	char * chr;
	int pos;
	char * ID;
	char * ref;
	char * alt;
	char * qual;
	char * filter;
	char * n1;
	char * n2;
	char * n3;

};

char * get_vcf(char * filename)
{
	char * file_contents;
	long input_file_size;
	FILE *input_file = fopen(filename,"rb");
	fseek(input_file,0,SEEK_END);
	input_file_size = ftell(input_file);
	rewind(input_file);
	file_contents = (char *) malloc(input_file_size * (sizeof(char)));
	fread(file_contents,sizeof(char),input_file_size,input_file);
	fclose(input_file);
	return file_contents;
}

void read_vcf (char * filename, struct vcf * vcf_lines)
{
	char * s = get_vcf(filename);
	char * newline_split;
	char * tab_split;
	char *saveptr1, *saveptr2;
	char *temp[10];		
	
	newline_split = strtok_r(s,"\n",&saveptr1);
	char metadata_skip = newline_split[0];
	
	int m=0;
	while(metadata_skip==35) //# is 35 in decimal
	{
		vcf_lines[m].chr=newline_split;
		newline_split = strtok_r(NULL,"\n",&saveptr1);
		metadata_skip = newline_split[0];
		m++;
	}
	
	for(int j=m;newline_split !=NULL;j++)
	{
		tab_split = strtok_r(newline_split,"	",&saveptr2);
		for(int i=0;tab_split !=NULL;i++)
		{
			temp[i]=tab_split;
			tab_split = strtok_r(NULL,"	",&saveptr2);
		}
		vcf_lines[j].chr=temp[0];
		vcf_lines[j].pos=atoi(temp[1]);
		vcf_lines[j].ID=temp[2];
		vcf_lines[j].ref=temp[3];
		vcf_lines[j].alt=temp[4];
		vcf_lines[j].qual=temp[5];
		vcf_lines[j].filter=temp[6];
		vcf_lines[j].n1=temp[7];
		vcf_lines[j].n2=temp[8];
		vcf_lines[j].n3=temp[9];
		
		newline_split = strtok_r(NULL,"\n",&saveptr1);
	}
}

void write_vcf(char * filename, struct gff * test_vcf, unsigned long size)
{
	FILE *f = fopen(filename, "w");
	if (f == NULL)
	{
	    printf("Error opening file!\n");
	    exit(1);
	}
	
	int n = size/(sizeof(struct vcf));
	for(int i=0;i<n;i++)
	{
		fprintf(f,"%s	%s	%s	%i	%i	%s	%s	%s	%s\n",
			test_vcf[i].chr,
			test_vcf[i].data1,
			test_vcf[i].feature,
			test_vcf[i].start,
			test_vcf[i].end,
			test_vcf[i].data2,
			test_vcf[i].data3,
			test_vcf[i].data4,
			test_vcf[i].the_rest);
	}

}