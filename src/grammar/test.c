typedef int uuu_int;

int function(uuu_int* ptr);
void recurse(int x);

int printf(const char* format, ...);

typedef struct str1tests
{
    int a;
    int b[10];
    uuu_int c;
} str1test;

enum testen 
{
    AAA,
    BBB=3,
    CCC
};

int test_glob_arr[2][3];

int main(int args, char** argv)
{
    int x,y;
    uuu_int* z;
    x=-(-10);
    int zz = (uuu_int)x;
    y=+1;
    str1test s;
    s.a=x;
    for(int i = 0; i<10; i++)
    {
        s.b[i]=y;
        y+=1;
    }
    struct str1tests s2;
    s2=s;
    int q = s2.a;
    //q should be 10
    printf("1. %d should be 10\n", q);
    enum testen qen = AAA;
    qen = BBB;
    //qen should be 3
    printf("2. %d should be 3\n", qen);
    q = sizeof(str1test);
    //q should be 48
    printf("3. %d should be 48\n", q);
    int sss=4;
    q = sizeof sss;
    //q should be 4
    printf("4. %d should be 4\n", q);
    q = AAA;
    //q should be 0
    printf("5. %d should be 0\n", q);
    q = CCC;
    //q should be 4
    printf("6. %d should be 4\n", q);
    int j=0;
    int result=1;
    do
    {
        result *= s.b[j];
        j++;
    } while(j<10);
    int *resultptr = &result;
    int res2 =*resultptr;
    *resultptr = res2 - 3;
    //result should be 2*3*4*5*6*7*8*9*10 - 3
    printf("7. %d should be 3628797\n", result);
    //s.a should be 10
    printf("8. %d should be 10\n", s.a);
    int b=function(resultptr);
    //result should be 3
    printf("9. %d should be 3\n",result);
    //b should be 6
    printf("10. %d should be 2\n", b);
    int c = result==4;
    //c should be 0
    printf("11. %d should be 0\n", c);
    int d=!c;
    //d should be 1
    printf("12. %d should be 1\n", d);
    if(c)
    {
        result=5;
    }
    else if(d)
    {
        result = 11;
    }
    else
    {
        result = 17;
    }
    //result should be 11;
    printf("13. %d should be 11\n", result);
    switch(result)
    {
    case 4: result=23; break;
    case 11: result = 24; break;
    }
    //result should be 24
    printf("14. %d should be 24\n", result);
    switch(result)
    {
    case 3: result=3;
    default: result = 5;
    }
    //result should be 5
    printf("15. %d should be 5\n", result);
    result = 1;
    c = 1;
    while(1)
    {
        if(result++==1)
            continue;
        c++;
        if(result==3)
            break;
    }
    //result should be 2
    printf("16. %d should be 2\n", result);
    //c should be 2
    printf("17. %d should be 2\n", c);
    d = ((result/c)*36+11-3)%5;
    //d should be 4
    printf("18. %d should be 4\n", d);
    double e = (1.0/2.0)*3+2-4;
    int ok = e<-0.25;
    //ok should be 1
    printf("19. %d should be 1\n", ok);
    ok = e>10;
    //ok should be 0
    printf("20. %d should be 0\n", ok);
    ok = 3>4;
    //ok should be 0
    printf("21. %d should be 0\n", ok);
    ok = 4>=4;
    //ok should be 1
    printf("22. %d should be 1\n", ok);
    ok = 4>4;
    //ok should be 0
    printf("23. %d should be 0\n", ok);
    ok = 4<4;
    //ok should be 0
    printf("24. %d should be 0\n", ok);
    ok = 4<=4;
    //ok should be 1
    printf("25. %d should be 1\n", ok);
    ok = 4||0;
    //ok should be 1
    printf("26. %d should be 1\n", ok);
    ok = 4&&0;
    //ok should be 0
    printf("27. %d should be 0\n", ok);
    ok = (4,5,6);
    //ok should be 6
    printf("28. %d should be 6\n", ok);
    recurse(10);
    test_glob_arr[1][3]=10;
    printf("29. %d should be 19\n", test_glob_arr[1][3]);
    return 0;
}

int function(int* ptr)
{
    int x = 1;
    *ptr = ((x<<3>>2)^1);
    int ret = (*ptr | 4) & ~5;
    return ret;
}

void recurse(int x)
{
    if(x==0)
        return;
    recurse(x-1);
}
